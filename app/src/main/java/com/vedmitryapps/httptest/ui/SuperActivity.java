package com.vedmitryapps.httptest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vedmitryapps.httptest.EndlessRecyclerViewScrollListener;
import com.vedmitryapps.httptest.PictureLoaderCallbacks;
import com.vedmitryapps.httptest.R;
import com.vedmitryapps.httptest.api.model.Picture;
import com.vedmitryapps.httptest.presenters.MessageView;
import com.vedmitryapps.httptest.ui.adapter.PicturesAdapter;
import com.vedmitryapps.httptest.ui.adapter.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class SuperActivity extends AppCompatActivity implements MessageView {

    private static final String LAYOUT_MANAGER_KEY = "lmk";
    RecyclerView recyclerView;
    List<Picture> pictures;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umorili);

        pictures = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.posts_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

       //final Presenter presenter = new Presenter(this);

        final PicturesAdapter adapter = new PicturesAdapter(pictures, this);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                System.out.println("scroll page - " + page);
                System.out.println("scroll total  - " + totalItemsCount);
                Log.i("TAG22", "page - " + page);
                // presenter.onScrollToEnd(page);
                initLoader(page);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       showBigPicture(position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
      //  presenter.onScrollToEnd(1);

        getSupportLoaderManager().initLoader(R.id.posts_recycle_view, Bundle.EMPTY, new PictureLoaderCallbacks(getApplicationContext(), this, 1));
    }

    void initLoader(int page){
        Log.i("TAG22", "initLoader page - " + page);
        getSupportLoaderManager().initLoader(R.id.posts_recycle_view + page, Bundle.EMPTY, new PictureLoaderCallbacks(getApplicationContext(), this, page));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LAYOUT_MANAGER_KEY, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable recyclerViewLayoutState = savedInstanceState.getParcelable(LAYOUT_MANAGER_KEY);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewLayoutState);
    }

    @Override
    public void updateList(List<Picture> list) {
        pictures.addAll(list);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(SuperActivity.this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBigPicture(int pos) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("pic",  pictures.get(pos).getUrls().getFull());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        Log.i("TAG22", "onRestart" );
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG22", "onStart" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG22", "onStop" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG22", "onDestroy" );

    }
}
