package com.spring.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.springboot.controller.DTO.StudentCheckDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentCheckDTOMapper extends BaseMapper<com.spring.springboot.controller.DTO.StudentCheckDTO> {
    @Select("SELECT s.student_name AS studentName, s.student_id AS studentId, s.class_name AS className, s.student_phone As studentPhone, " +
            "(CASE WHEN c.time IS NOT NULL THEN true ELSE false END) AS checkIn " +
            "FROM student s " +
            "LEFT JOIN card c ON s.student_id = c.student_id AND date_format(c.time, '%Y-%m-%d') = #{date} " +
            "WHERE s.class_name = #{className}")
    List<StudentCheckDTO> getStudentCheckList(@Param("className") String className, @Param("date") String date);
}