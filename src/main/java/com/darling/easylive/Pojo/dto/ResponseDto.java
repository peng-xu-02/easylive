package com.darling.easylive.Pojo.dto;


import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public ResponseDto(){
        code=200;
        message="ok";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
