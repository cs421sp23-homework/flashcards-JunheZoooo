package com.example.windowscardmanage.utils;

import com.auth0.jwt.JWT;
import com.example.windowscardmanage.entity.User;
import com.example.windowscardmanage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * token 工具类
 */
@Component
public class TokenUtils {
    @Autowired
    private UserMapper userMapperOld;
    private static UserMapper userMapper;

    @PostConstruct
    public void init() {
        userMapper = this.userMapperOld;
    }

    /**
     * 根据当前 token 登录用户 id 获取登录用户信息
     */
    public static User getLoginUser() {
        return userMapper.selectById(getTokenUserId());
    }

    // 获取 token 的 userId
    public static String getTokenUserId () {
        String token = Objects.requireNonNull(getRequest()).getHeader("token"); // 从请求头中取出 token
        return JWT.decode(token).getAudience().get(0);
    }
    /**
     * 获取 request
     */
    public static HttpServletRequest getRequest () {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
