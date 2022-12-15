package com.spring.springboot.controller;

import com.spring.springboot.common.Result;
import com.spring.springboot.controller.DTO.LoginDTO;
import com.spring.springboot.controller.DTO.StudentDTO;
import com.spring.springboot.controller.DTO.TeacherDTO;
import com.spring.springboot.entity.Student;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.mapper.StudentMapper;
import com.spring.springboot.mapper.TeacherMapper;
import com.spring.springboot.service.StudentService;
import com.spring.springboot.service.TeacherService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;

    @PostMapping("")
    public Result login(@RequestBody LoginDTO loginDTO){
        Integer radio = loginDTO.getRadio();
        if (radio==0){              //用来区分是教师端登录还是学生端登录
            Student student = new Student();
            student.setStudentName(loginDTO.getUsername());
            String password = DigestUtils.sha1Hex(loginDTO.getPassword());
            student.setStudentPassword(password);
            StudentDTO stu = studentService.login(student);
            stu.setState(0);       //用来给前端判断是学生还是老师，学生0，老师1

            return Result.success(stu);

        }else if (radio==1){
            Teacher teacher = new Teacher();
            teacher.setTeacherName(loginDTO.getUsername());
            String password = DigestUtils.sha1Hex(loginDTO.getPassword());
            teacher.setTeacherPassword(password);
            TeacherDTO tea = teacherService.login(teacher);
            tea.setState(1);
            return Result.success(tea);
        }else{
            return Result.error("405","输入正确的radio");
        }


    }

//    @Test
//     public static void main(String[] args) {
//        String test01 = "123456";
//        System.out.println(DigestUtils.sha1Hex(test01));
//    }

}
