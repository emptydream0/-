package com.spring.springboot.controller.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDTO {
    private Integer studentId;
    private String studentName;
    private String studentPassword;
    private String studentPhone;
    private String token;
    private String Authorization;
    private String info;
    private String sex;
    private String birthday;
    private List<String> city;
    private String university;
    private String className;
    private Integer state;
    private String icon;



}
