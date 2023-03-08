package com.spring.springboot.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.springboot.common.Result;
import com.spring.springboot.controller.DTO.StudentDTO;
import com.spring.springboot.entity.Card;
import com.spring.springboot.entity.Note;
import com.spring.springboot.entity.Student;
import com.spring.springboot.service.CardService;
import com.spring.springboot.service.NoteService;
import com.spring.springboot.service.StudentService;
import com.spring.springboot.utils.TimeUtils;
import com.spring.springboot.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CardService cardService;

    @PostMapping("/findAll")
    public Result findAll(){
        return Result.success(studentService.list());
    }


    @PostMapping("/update")
    private Result update(@RequestBody StudentDTO studentDTO){
        if (studentDTO.getStudentId()==null){
            return Result.error("405","需要studentId");
        }
        Student student =new Student();
        BeanUtil.copyProperties(studentDTO,student,"city");
        studentService.cityTrans(studentDTO,student);
        studentService.saveOrUpdate(student);
        Student studentDTOreturn = studentService.getById(studentDTO.getStudentId());
        return Result.success(studentDTOreturn);
    }
    @PostMapping("/getid")
    private Result getId(){
        return Result.success(TokenUtils.getUserId());
    }
    @GetMapping("/getbyid/{id}")
    private Result getById(@PathVariable Integer id){
        Student byId = studentService.getById(id);
        return Result.success(byId);
    }


    @PostMapping("/absence")
    public Result absence(@RequestBody Note note){
        noteService.save(note);
        return Result.success("提交成功，假条待审核");
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Integer studentId = TokenUtils.getUserId();
        IPage<Note> page= new Page<>(pageNum,pageSize);
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.orderByAsc("note_enable");
        queryWrapper.orderByDesc("grant_time");
        IPage<Note> page1 = noteService.page(page,queryWrapper);
        return Result.success(page1);

    }
    @GetMapping("/leave")
    public Result leave(@RequestParam Integer studentId){
        String timeNow = TimeUtils.getStringDate();
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("note_enable",1);
        queryWrapper.le("leave_time",timeNow);
        queryWrapper.ge("return_time",timeNow);
        List<Note> notes = noteService.list(queryWrapper);
        return Result.success(notes);
    }






    @PostMapping("/beatCard")
    public Result beatCard(@RequestBody Card card){
        Integer studentId = card.getStudentId();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayString = today.format(formatter);            //获取今天的时间

        LocalDate date = LocalDate.parse(todayString);
        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

         //生成今日的开始时间和结束时间

        QueryWrapper<Card> qr =new QueryWrapper<>();
        qr.eq("student_id",studentId);
        qr.between("time",startTime,endTime);
        List<Card> cards = cardService.list(qr);                //查看今日是否有过打卡，如果没有则允许打卡，反之亦然
        if (cards.isEmpty()){
            System.out.println("cards的长度："+cards.size()+cards.isEmpty());
            card.setTime(TimeUtils.getStringDate());
            cardService.save(card);
            return Result.success("提交成功");
        }else return Result.success("今日已经打过卡");



    }








    @PostMapping("/getCard")
    public Result getCard(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Integer studentID=TokenUtils.getUserId();
        IPage<Card> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Card> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",studentID);
        queryWrapper.orderByDesc("time");
        IPage<Card> page1=cardService.page(page,queryWrapper);
        return Result.success(page1);
    }





}
