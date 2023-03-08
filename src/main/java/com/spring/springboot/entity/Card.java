package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Card {
    @TableId(type = IdType.AUTO)
    private Integer cardId;
    private Integer studentId;
    private String studentName;
    private String time;
    private String address;
    private String addressName;
    private String health;
    private String morningTemperature;
    private String middayTemperature;

}
