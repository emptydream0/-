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
import com.spring.springboot.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/beatCard")
    public Result beatCard(@RequestBody Card card){
        cardService.save(card);
        return Result.success("提交成功");
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
