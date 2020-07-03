package com.zhen.base.net;

public class ApiException extends RuntimeException {

    private int resultCode;

    public ApiException(int resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
