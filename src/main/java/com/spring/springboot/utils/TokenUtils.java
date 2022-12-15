package com.spring.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {
    public static String getToken(String userId,String sign){
       return JWT.create().withAudience(userId)//将userid保存再token中，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时过期
                .sign(Algorithm.HMAC256(sign));  //以password作为token的密钥
    }


    public static Integer getUserId(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                Integer Id=Integer.valueOf(userId);
                return Id;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
