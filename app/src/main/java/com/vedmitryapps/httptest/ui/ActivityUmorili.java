package com.vedmitryapps.httptest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vedmitryapps.httptest.R;
import com.vedmitryapps.httptest.api.model.Picture;
import com.vedmitryapps.httptest.api.model.PostModel;
import com.vedmitryapps.httptest.api.service.UmoriliApi;
import com.vedmitryapps.httptest.api.service.UnsplashClient;
import com.vedmitryapps.httptest.ui.adapter.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityUmorili extends AppCompatActivity {

    RecyclerView recyclerView;
    List<PostModel> posts;


   UmoriliApi umoriliApi;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umorili);

        posts = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PostsAdapter adapter = new PostsAdapter(posts);
        recyclerView.setAdapter(adapter);


    /*    retrofit = new Retrofit.Builder()
                .baseUrl("http://www.umori.li/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        umoriliApi = retrofit.create(UmoriliApi.class);

        umoriliApi.getData("bash", 50).enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                posts.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(ActivityUmorili.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });*/

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UnsplashClient client = retrofit.create(UnsplashClient.class);

        Call<List<Picture>> call = client.getPictures("264bdb53c80599fa1f9cc027aae4e8a2bf3798551a00828df44750be8a784e3b", 2);

        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                System.out.println("qqqq = " + call.request().toString());
                System.out.println("qqqq = " + call.request().body());
                for (Picture pic:response.body()
                     ) {
                    System.out.println("qqqq = " + pic.getId());
                    System.out.println("qqqq = " + pic.getUser());
                }
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {

            }
        });

    }
}
