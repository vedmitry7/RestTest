package com.vedmitryapps.httptest.api.service;

import com.vedmitryapps.httptest.App;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("appid", App.CLIENT_ID)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}