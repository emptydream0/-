package com.spring.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.springboot.controller.DTO.TeacherDTO;
import com.spring.springboot.controller.DTO.TeacherDTO;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.exception.ServiceException;
import com.spring.springboot.mapper.TeacherMapper;
import com.spring.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {
    @Autowired
    private TeacherMapper teacherMapper;
    public TeacherDTO login(Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_name",teacher.getTeacherName()).or().eq("teacher_id",teacher.getTeacherName());
        queryWrapper.eq("teacher_password",teacher.getTeacherPassword());
        Teacher one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException("500","系统错误");
        }
        TeacherDTO teacherDTO = new TeacherDTO();
        if (one!=null){
            BeanUtil.copyProperties(one,teacherDTO,true);
            String token = TokenUtils.getToken(teacherDTO.getTeacherId().toString(),teacherDTO.getTeacherPassword());
            teacherDTO.setToken(token);
            teacherDTO.setAuthorization(token);
            return teacherDTO;
        }else {
            throw new ServiceException("500","用户名或密码错误");
        }
    }

    public void cityTrans(TeacherDTO teacherDTO,Teacher teacher){
        List<String> cityDto = teacherDTO.getCity();
        String city= "";
        for (int i=0;i<cityDto.size();i++){
            city=city+cityDto.get(i);
            if (i<(cityDto.size()-1)){
                city=city+",";
            }
        }
        teacher.setCity(city);
    }

    public Boolean idSame(Integer teacherId){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        Long number = teacherMapper.selectCount(queryWrapper);
        if (number>0){
            return true;

        }else{
            return false;
        }


    }
    
}
