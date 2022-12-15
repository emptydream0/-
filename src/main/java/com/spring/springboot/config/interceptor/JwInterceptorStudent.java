package com.spring.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spring.springboot.common.Constants;
import com.spring.springboot.entity.Student;
import com.spring.springboot.entity.Teacher;
import com.spring.springboot.exception.ServiceException;
import com.spring.springboot.service.StudentService;
import com.spring.springboot.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwInterceptorStudent implements HandlerInterceptor {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        不是映射方法则直接通过
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        if ((StrUtil.isBlank(token))){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new  ServiceException(Constants.CODE_401,"token验证失败");
        }
        Student student =studentService.getById(userId);
        if (student==null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(student.getStudentPassword())).build();
            try {
                jwtVerifier.verify(token);
            }catch (JWTVerificationException e){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;






    }
}
