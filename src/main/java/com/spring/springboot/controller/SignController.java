package com.spring.springboot.controller;

import com.spring.springboot.common.Result;
import com.spring.springboot.controller.DTO.SignDTO;
import com.spring.springboot.entity.Student;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.mapper.StudentMapper;
import com.spring.springboot.mapper.TeacherMapper;
import com.spring.springboot.service.StudentService;
import com.spring.springboot.service.TeacherService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @PostMapping("")
    public Result sign(@RequestBody SignDTO signDTO){
        Integer radio = signDTO.getRadio();
        System.out.println(radio);
        if (radio==0){
            Student student = new Student();
            student.setStudentName(signDTO.getUsername());
            String password = DigestUtils.sha1Hex(signDTO.getPassword());
            student.setStudentPassword(password);
            student.setStudentPhone(signDTO.getPhone());
            student.setStudentId(signDTO.getId());
            if (studentService.idSame(student.getStudentId())){
                return Result.error("400","学生id重复");
            }
            studentService.save(student);
            return Result.success("学生注册成功");
        }else if (radio==1){
            Teacher teacher = new Teacher();
            teacher.setTeacherName(signDTO.getUsername());
            String password = DigestUtils.sha1Hex(signDTO.getPassword());
            teacher.setTeacherPassword(password);
            teacher.setTeacherPhone(signDTO.getPhone());
            teacher.setTeacherId(signDTO.getId());
            if (teacherService.idSame(teacher.getTeacherId())){
                return Result.error("400","教师id重复");
            }
            teacherService.save(teacher);
            return Result.success("教师注册成功");

        }else {
            return Result.error("405","请输入正确的radio");
        }
    }
    
}
