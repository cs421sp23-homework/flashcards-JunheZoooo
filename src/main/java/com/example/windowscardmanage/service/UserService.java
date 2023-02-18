package com.example.windowscardmanage.service;

import com.example.windowscardmanage.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* 登录用户的 Service 接口
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     */
    void register(User user);

    /**
     * 用户登录
     */
    User login(String account,String password);

    /**
     * 根据用户账号获取用户数据
     */
    User getByAccount(String account);

    /**
     * 根据用户 id 获取用户数据
     */
    User getUser(Integer userId);
}
