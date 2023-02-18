package com.example.windowscardmanage.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.windowscardmanage.entity.User;
import org.springframework.util.DigestUtils;

import java.util.Date;

public class StringUtils {
    /**
     * 生成 token 数据
     * @param user 用户数据
     * @return token
     */
    public static String getToken(User user) {
        Date nowDate = new Date(); // 当前时间
        Date expiresDate = DateAbout.getAfterDate(nowDate,0,0,7,0,0,0);
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getId())) // 将 user id 保存到 token 中
                .withIssuedAt(nowDate) // 签名生成时间
                .withExpiresAt(expiresDate) // 7 天后 token 过期
                .sign(Algorithm.HMAC256(user.getPassword())); // 以 password 作为 token 的密钥
        return token;
    }
    /**
     * 执行密码加密 【加密算法】
     *
     * @param password 原密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    public static String getMd5Password(String password, String salt) {
        // 加密规则：1.无视原始密码的强度、2.使用 UUID 作为盐值，在原始密码的左右两侧拼接、3.循环加密 3 次
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
