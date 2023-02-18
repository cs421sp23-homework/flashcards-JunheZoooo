package com.example.windowscardmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.windowscardmanage.entity.User;
import com.example.windowscardmanage.service.UserService;
import com.example.windowscardmanage.mapper.UserMapper;
import com.example.windowscardmanage.utils.ObjectUtils;
import com.example.windowscardmanage.utils.StringUtils;
import com.example.windowscardmanage.utils.UpdateException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public void register(User user) {
        // get user data result by account
        User result = getByAccount(user.getAccount());
        if (ObjectUtils.isEmpty(result)) { // no user data result
            // create salt
            String salt = UUID.randomUUID().toString().toUpperCase();
            // create password
            String md5Password = StringUtils.getMd5Password(user.getPassword(),salt);
            user.setPassword(md5Password);// set password
            // set salt
            user.setSalt(salt);
            // set create time
            user.setCreateTime(new Date());
            // create data
            boolean row = save(user);
            if (!row) {
                throw new UpdateException("register error,please contact system admin");
            }
        } else { // exist result
            // throw exception
            throw new UpdateException("account already exist");
        }
    }

    @Override
    public User login(String account, String password) {
        // get user data by account
        User result = getByAccount(account);
        // result is not empty
        if (ObjectUtils.isNotEmpty(result)) {
            // get user set salt
            String salt = result.getSalt();
            // create md5 password for front end login password
            String md5Password = StringUtils.getMd5Password(password,salt);
            // Judge whether the passwords are consistent
            if (result.getPassword().equals(md5Password)) {// true
                // result
                return result;
            } else { // false
                throw new UpdateException("password error");
            }
        } else {
            throw new UpdateException("account does not exist");
        }
    }

    @Override
    public User getByAccount(String account) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getAccount,account);
        return getOne(lqw);
    }

    @Override
    public User getUser(Integer userId) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getId,userId);
        return getOne(lqw);
    }
}




