package com.vedmitryapps.httptest;

import android.app.Application;
import android.util.Log;

import com.vedmitryapps.httptest.api.service.UnsplashClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static final String CLIENT_ID = "264bdb53c80599fa1f9cc027aae4e8a2bf3798551a00828df44750be8a784e3b";
    private static UnsplashClient client;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

        client = retrofit.create(UnsplashClient.class);
    }

    public static UnsplashClient getApi() {
        Log.i("TAG22", "getApi()" );
        return client;
    }
}