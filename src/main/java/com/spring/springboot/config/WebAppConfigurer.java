package com.spring.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer  implements WebMvcConfigurer {

    /**
     * 配置虚拟路径映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射 ，一下位windows下的
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/glmg/**")
                .addResourceLocations("file:E:\\Learning\\学生外出请假平台\\学生外出请假平台\\src\\main\\files\\");  // 轮转帖子图片映射
    }

}