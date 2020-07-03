package com.zhen.base.net;

import java.io.Serializable;

public class ApiRequestBean<T> implements Serializable {

    private T result;
    private int code;
    private String codeMsg;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }
}
