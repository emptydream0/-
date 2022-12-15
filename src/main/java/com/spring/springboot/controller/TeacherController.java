package com.spring.springboot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.springboot.common.Result;
import com.spring.springboot.controller.DTO.TeacherDTO;
import com.spring.springboot.controller.DTO.TeacherDTO;
import com.spring.springboot.entity.Note;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.service.CardService;
import com.spring.springboot.service.NoteService;
import com.spring.springboot.service.TeacherService;
import com.spring.springboot.utils.TimeUtils;
import com.spring.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CardService cardService;
    @PostMapping("/findAll")
    public Result findAll(){
        return Result.success(teacherService.list());
    }


    @PostMapping("/update")
    private Result update(@RequestBody TeacherDTO teacherDTO){
        if (teacherDTO.getTeacherId()==null){
            return Result.error("405","需要teacherId");
        }
        Teacher teacher =new Teacher();
        BeanUtil.copyProperties(teacherDTO,teacher,"city");
        teacherService.cityTrans(teacherDTO,teacher);
        teacherService.saveOrUpdate(teacher);
        Teacher teacherDTOreturn = teacherService.getById(teacherDTO.getTeacherId());
        return Result.success(teacherDTOreturn);
    }
    @PostMapping("/getid")
    private Result getId(){
        return Result.success(TokenUtils.getUserId());
    }
    @GetMapping("/getbyid/{id}")
    private Result getById(@PathVariable Integer id){
        Teacher byId = teacherService.getById(id);
        return Result.success(byId);
    }

    @PostMapping("/grant")
    private Result grant(@RequestParam Integer teacherId,@RequestParam String teacherName,@RequestParam Integer noteId,@RequestParam Integer grant){
        Note note = new Note();
        new Note();
        Note noteReturn;
        note.setNoteId(noteId);
        note.setTeacherId(teacherId);
        note.setTeacherName(teacherName);
        note.setNoteEnable(grant);
        note.setGrantTime(TimeUtils.getStringDate());
        noteService.updateById(note);
        noteReturn=noteService.getById(noteId);
        return Result.success(noteReturn);


    }
    @GetMapping("/getNotes")
    private Result getNotes(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String name,@RequestParam String className){
        IPage<Note> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(Wrapper-> Wrapper.like("student_name",name).or().like("student_id",name));
        queryWrapper.eq("class_name",className);
        queryWrapper.orderByAsc("note_enable");
        queryWrapper.orderByDesc("grant_time");
        IPage<Note> page1 = noteService.page(page,queryWrapper);
        return Result.success(page1);
    }


    @GetMapping("/getNotes0")
    private Result getNotes0(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String className){
        IPage<Note> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_name",className);
        queryWrapper.eq("note_enable",0);
        queryWrapper.orderByDesc("grant_time");
        IPage<Note> page1 = noteService.page(page,queryWrapper);
        return Result.success(page1);
    }





}
