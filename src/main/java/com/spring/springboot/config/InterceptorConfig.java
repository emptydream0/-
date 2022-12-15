package com.spring.springboot.config;

import com.spring.springboot.config.interceptor.JwInterceptorStudent;
import com.spring.springboot.config.interceptor.JwInterceptorTeacher;
import com.spring.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
@Configuration
public class InterceptorConfig extends WebAppConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")//拦截所有请求，通过token判断是否需要登录
                .excludePathPatterns("/login","/sign","/teacher","student");
        registry.addInterceptor(jwInterceptorTeacher())
                .addPathPatterns("/teacher");
        registry.addInterceptor(jwtInterceptorStudent())
                .addPathPatterns("/student");

    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    @Bean
    public JwInterceptorStudent jwtInterceptorStudent(){return new JwInterceptorStudent();}
    @Bean
    public JwInterceptorTeacher jwInterceptorTeacher(){return new JwInterceptorTeacher();}

}
