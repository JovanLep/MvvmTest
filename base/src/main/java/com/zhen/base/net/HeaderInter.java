package com.zhen.base.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Lierpeng
 */
public class HeaderInter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Connection connection = chain.connection();
        Response proceed = chain.proceed(request);
        Call call = chain.call();
        RequestBody body = request.body();

        Request.Builder builder = request.newBuilder();
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");
        builder.addHeader("","");



        return null;
    }
}
