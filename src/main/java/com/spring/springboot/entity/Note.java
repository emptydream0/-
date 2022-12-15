package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Note {
    @TableId(type = IdType.AUTO)
    private Integer noteId;
    private Integer studentId;
    private String studentName;
    private String studentPhone;
    private String className;
    private String university;
    private Integer teacherId;
    private String teacherName;
    private String noteType;
    private String noteRange;
    private String leaveTime;
    private String returnTime;
    private String noteDestination;
    private String noteReason;
    private String noteInfo;
    private Integer noteEnable;
    private String grantTime;
}
