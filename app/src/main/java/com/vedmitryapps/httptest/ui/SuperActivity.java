package com.vedmitryapps.httptest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vedmitryapps.httptest.EndlessRecyclerViewScrollListener;
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

    UnsplashClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umorili);

        pictures = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        final PicturesAdapter adapter = new PicturesAdapter(pictures, this);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                System.out.println("scroll page - " + page);
                System.out.println("scroll total  - " + totalItemsCount);
              //  if(page > 1)
                loadNextPictures(page);
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        client = retrofit.create(UnsplashClient.class);

        /*Call<List<Picture>> call = client.getPictures("264bdb53c80599fa1f9cc027aae4e8a2bf3798551a00828df44750be8a784e3b", 1, 30);

        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                System.out.println("qqqq = " + response.body().size());
                for (Picture pic:response.body()
                     ) {
                    System.out.println("qqqq = " + pic.getId());
                }

                pictures.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                Toast.makeText(SuperActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });*/
        loadNextPictures(1);

    }

    void loadNextPictures( int page){

        System.out.println("scroll - loadNextPictures " + page);

        Call<List<Picture>> call = client.getPictures("264bdb53c80599fa1f9cc027aae4e8a2bf3798551a00828df44750be8a784e3b", page, 30);

        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                System.out.println("qqqq = " + response.body().size());
                for (Picture pic:response.body()
                        ) {
                    System.out.println("qqqq = " + pic.getId());
                }
                System.out.println("scroll - " + pictures.size());

                pictures.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                Toast.makeText(SuperActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
