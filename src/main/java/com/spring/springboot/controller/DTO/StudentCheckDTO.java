package com.spring.springboot.controller.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StudentCheckDTO {
    private Integer studentId;
    private String studentName;
    private String className;
    private String studentPhone;
    private Boolean checkIn;
}
