package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Student {
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    private String studentName;
    private String studentPassword;
    private String studentPhone;
    private String info;
    private String sex;
    private String birthday;
    private String city;
    private String university;
    private String className;
    private String icon;




}
