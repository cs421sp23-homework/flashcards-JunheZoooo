package com.example.windowscardmanage.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user data table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    /**
     * user id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * user account
     */
    private String account;

    /**
     * user password
     */
    private String password;

    /**
     * salt：for password encryption'
     */
    private String salt;

    /**
     * create time
     */
    private Date createTime;
}