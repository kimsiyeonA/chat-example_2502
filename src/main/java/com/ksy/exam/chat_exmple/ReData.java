package com.ksy.exam.chat_exmple;


import lombok.Data;


@Data
public class ReData<T> {
    private String resultCode;
    private String msg;
    private T data; // <T>  타입을 나중에 정하겠다.
    public ReData(String resultCode, String msg, T data) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.data = data;
    }

}
