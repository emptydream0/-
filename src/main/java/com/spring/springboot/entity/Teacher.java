package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Teacher {
    @TableId(type = IdType.INPUT)
    private Integer teacherId;
    private String teacherName;
    private String teacherPassword;
    private String teacherPhone;
    private String info;
    private String sex;
    private String birthday;
    private String city;
    private String university;
    private String className;
    private String icon;


}
