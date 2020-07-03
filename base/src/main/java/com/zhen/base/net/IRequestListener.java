package com.zhen.base.net;

public interface IRequestListener<T> {

    void onSuccess(T obj);

    void onError(int errorCode, String errorMsg);
}