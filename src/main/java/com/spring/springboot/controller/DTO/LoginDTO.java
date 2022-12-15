package com.spring.springboot.controller.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private Integer radio;
    private String token;
    private String Authorization;
}
