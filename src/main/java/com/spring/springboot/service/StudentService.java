package com.spring.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.springboot.controller.DTO.StudentDTO;
import com.spring.springboot.controller.DTO.StudentCheckDTO;
import com.spring.springboot.entity.Student;
import com.spring.springboot.exception.ServiceException;
import com.spring.springboot.mapper.StudentMapper;
import com.spring.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentService extends ServiceImpl<StudentMapper,Student> {
    @Autowired
    private StudentMapper studentMapper;

    public StudentDTO   login(Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_name",student.getStudentName()).or().eq("student_id",student.getStudentName());
        queryWrapper.eq("student_password",student.getStudentPassword());
        Student one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException("500","系统错误");
        }
        StudentDTO studentDTO = new StudentDTO();
        if (one!=null){
            BeanUtil.copyProperties(one,studentDTO,true);
            String token = TokenUtils.getToken(studentDTO.getStudentId().toString(),studentDTO.getStudentPassword());
            studentDTO.setToken(token);
            studentDTO.setAuthorization(token);
            return studentDTO;
        }else {
            throw new ServiceException("500","用户名或密码错误了");
        }
    }

    public void cityTrans(StudentDTO studentDTO,Student student){
        List<String> cityDto = studentDTO.getCity();
        String city= "";
        for (int i=0;i<cityDto.size();i++){
            city=city+cityDto.get(i);
            if (i<(cityDto.size()-1)){
                city=city+",";
            }
        }
        student.setCity(city);
    }

    public Boolean idSame(Integer studentId){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        Long number = studentMapper.selectCount(queryWrapper);
        if (number>0){
            return true;

        }else{
            return false;
        }


    }








}
