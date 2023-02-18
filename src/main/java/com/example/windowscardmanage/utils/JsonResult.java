package com.example.windowscardmanage.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 创建 Json，负责从后端获取数据后生成json传递前端
 * @param <E>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<E> implements Serializable {
    private static final long serialVersionUID = 212479167057243670L;
    // 状态
    private Integer code;
    // 错误描述
    private String message;
    // 数据
    private E data;

    public JsonResult(E data) {
        this.data = data;
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(String message, E data) {
        this.message = message;
        this.data = data;
    }

    public JsonResult(Integer code, E data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(Integer code) {
        this.code = code;
    }
}
