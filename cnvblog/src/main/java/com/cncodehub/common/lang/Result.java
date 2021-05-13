package com.cncodehub.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
//这里要序列化
public class Result<T> implements Serializable {

    //三个字段
    private int code;
    private String msg;
    private T data;

    public static<T> Result<T> succ200(T data){
        return succ(200,"成功",data);
    }

    public static<T> Result<T> succ400(T data){
        return succ(400,"请求错误",null);
    }

    public static<T> Result<T> succ(int code, String msg, T data){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
