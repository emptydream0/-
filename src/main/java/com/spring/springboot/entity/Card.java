package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Card {
    @TableId(type = IdType.AUTO)
    private Integer cardId;
    private Integer studentID;
    private String studentName;
    private String time;
    private String locationState;
    private String location;
    private String dormitory;
    private String barcodeCode;
    private String barcodeUrl;
    private String health;
    private String morningTemperature;
    private String middayTemperature;

}
