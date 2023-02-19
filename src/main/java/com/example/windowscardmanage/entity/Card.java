package com.example.windowscardmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * card
 */
@TableName(value ="card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    /** card id */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** card create user id */
    private Integer userId;

    /** card type：1.card package，2.card */
    private Integer cardType;

    /** card name */
    private String name;

    /** card ascription switch：0.no ascription，1.ascription：desktop no show */
    private Integer cardOfIs;

    /** when it is a card：ascription card package id */
    private Integer cardOfId;

    /** when it is a card：card content */
    private String content;

    /** setting card color style: 0.default 1.red 2.orange 3.yellow 4.green 5.blue */
    private Integer colorStyle;

    /** create time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** update time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}