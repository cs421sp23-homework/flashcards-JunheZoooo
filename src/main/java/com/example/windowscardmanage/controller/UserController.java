package com.example.windowscardmanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.windowscardmanage.entity.User;
import com.example.windowscardmanage.service.UserService;
import com.example.windowscardmanage.utils.JsonResult;
import com.example.windowscardmanage.utils.ObjectUtils;
import com.example.windowscardmanage.utils.StringUtils;
import com.example.windowscardmanage.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * password reset
     */
    @PostMapping("/forgetPassword")
    public JsonResult<Void> forgetPassword(@RequestBody JSONObject params) {
        String account = params.get("account").toString();
        String password = params.get("password").toString();
        // jr
        JsonResult<Void> jr;
        // get user data by account
        User user = userService.getByAccount(account);
        if (ObjectUtils.isNotEmpty(user)) {
            // create salt
            String salt = UUID.randomUUID().toString().toUpperCase();
            user.setSalt(salt);
            // create md5 password
            String md5Password = StringUtils.getMd5Password(password,salt);
            user.setPassword(md5Password);
            // update
            userService.updateById(user);
            jr = new JsonResult<>(200,"reset success");
        } else {
            jr = new JsonResult<>(403,"account does not exist");
        }
        return jr;
    }

    /**
     * user login
     */
    @PostMapping("/userLogin")
    public JsonResult<Map<String,Object>> userLogin(@RequestBody JSONObject params) {
        String account = params.get("account").toString();
        String password = params.get("password").toString();
        // jr
        JsonResult<Map<String,Object>> jr;
        try {
            Map<String,Object> map = new HashMap<>();
            // interface login
            User user = userService.login(account,password);
            jr = new JsonResult<>(200,"signing in");
            map.put("user",user);
            map.put("token", StringUtils.getToken(user));
            jr.setData(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            jr = new JsonResult<>(403,e.getMessage());
        }
        return jr;
    }

    /**
     * user register
     */
    @PostMapping("/userRegister")
    public JsonResult<Void> userRegister(@RequestBody User user) {
        // jr
        JsonResult<Void> jr;
        try {
            userService.register(user);
            jr = new JsonResult<>(200,"register success");
        } catch (RuntimeException e) {
            e.printStackTrace();
            jr = new JsonResult<>(403,e.getMessage());
        }
        return jr;
    }

    /**
     * get user login data
     */
    @PostMapping("/getUserData")
    public JsonResult<Map<String,Object>> getUserData() {
        User user = TokenUtils.getLoginUser();
        JsonResult<Map<String,Object>> jr;
        if (ObjectUtils.isNotEmpty(user)) {
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            jr = new JsonResult<>(200,"handle success");
            jr.setData(map);
        } else {
            jr = new JsonResult<>(403,"handle error");
        }
        return jr;
    }
}
