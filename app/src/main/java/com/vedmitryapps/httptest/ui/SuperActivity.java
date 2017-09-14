package com.vedmitryapps.httptest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vedmitryapps.httptest.R;
import com.vedmitryapps.httptest.api.model.Picture;
import com.vedmitryapps.httptest.api.service.UmoriliApi;
import com.vedmitryapps.httptest.api.service.UnsplashClient;
import com.vedmitryapps.httptest.ui.adapter.PicturesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SuperActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Picture> pictures;


   UmoriliApi umoriliApi;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umorili);

        pictures = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final PicturesAdapter adapter = new PicturesAdapter(pictures, this);
        recyclerView.setAdapter(adapter);


    /*    retrofit = new Retrofit.Builder()
                .baseUrl("http://www.umori.li/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        umoriliApi = retrofit.create(UmoriliApi.class);

        umoriliApi.getData("bash", 50).enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                pictures.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(SuperActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });*/

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UnsplashClient client = retrofit.create(UnsplashClient.class);

        Call<List<Picture>> call = client.getPictures("264bdb53c80599fa1f9cc027aae4e8a2bf3798551a00828df44750be8a784e3b", 10);

        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                System.out.println("qqqq = " + call.request().toString());
                for (Picture pic:response.body()
                     ) {
                    System.out.println("qqqq = " + pic.getId());
                    System.out.println("qqqq = " + pic.getUser().getBio());
                    System.out.println("qqqq = " + pic.getLinks().getDownload());
                    System.out.println("qqqq = " + pic.getLinks().getSelf());
                    System.out.println("qqqq = " + pic.getUrls().getSmall());
                    System.out.println("qqqq = " + pic.getUrls().getFull());
                    System.out.println("qqqq = " + pic.getUrls().getSmall());
                    System.out.println("qqqq = " + pic.getDescription());

                }


                pictures.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                Toast.makeText(SuperActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
