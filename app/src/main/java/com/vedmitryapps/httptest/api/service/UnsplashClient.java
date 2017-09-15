package com.vedmitryapps.httptest.api.service;


import com.vedmitryapps.httptest.api.model.Picture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashClient {

    @GET("/photos/curated")
    Call<List<Picture>> getPictures(@Query("client_id") String id,
                                    @Query("page") int page,
                                    @Query("per_page") int number);
}
