package com.spring.springboot.controller.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private Integer teacherId;
    private String teacherName;
    private String teacherPassword;
    private String teacherPhone;
    private String token;
    private String Authorization;
    private Integer state;
    private String info;
    private String sex;
    private String birthday;
    private List<String> city;
    private String university;
    private String className;
    private String icon;
}
