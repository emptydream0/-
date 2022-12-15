package com.spring.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file")
public class MyFile {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String type;
    private Long size;
    private String url;
    @TableLogic
    private Boolean delete;
    private Boolean enable;


}
