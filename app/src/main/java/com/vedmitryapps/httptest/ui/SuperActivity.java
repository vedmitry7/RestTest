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
    private int pageCount;

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
                pageCount = page;
                System.out.println("scroll page - " + page);
                System.out.println("scroll total  - " + totalItemsCount);
                Log.i("TAG22", "OnScrollListener page - " + page);
                // presenter.onScrollToEnd(page);
                if(page > pageCount)
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
        if(savedInstanceState!=null)
        pageCount = savedInstanceState.getInt("pageCount");
        Log.i("TAG22", "onCreate pageCount - " + pageCount);

        if(pageCount > 0) {
            for (int i = 0; i < pageCount; i++) {
                getSupportLoaderManager().initLoader(R.id.posts_recycle_view + 1 + i, Bundle.EMPTY, new PictureLoaderCallbacks(getApplicationContext(), this, 1 + i));
            }
        }   else {
            getSupportLoaderManager().initLoader(R.id.posts_recycle_view + 1, Bundle.EMPTY, new PictureLoaderCallbacks(getApplicationContext(), this, 1));
        }
    }

    void initLoader(int page){
        Log.i("TAG22", "initLoader page - " + page);
        getSupportLoaderManager().initLoader(R.id.posts_recycle_view + page, Bundle.EMPTY, new PictureLoaderCallbacks(getApplicationContext(), this, page));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LAYOUT_MANAGER_KEY, recyclerView.getLayoutManager().onSaveInstanceState());
        int currentVisiblePosition;
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt("recKey", currentVisiblePosition);
        Log.i("TAG22", "onSaveInstanceState " + currentVisiblePosition);


        outState.putInt("pageCount", pageCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable recyclerViewLayoutState = savedInstanceState.getParcelable(LAYOUT_MANAGER_KEY);
        //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewLayoutState);
        //Log.i("TAG22", "onRestoreInstanceState " + recyclerViewLayoutState.describeContents() + " .");

        int currentVisiblePosition = savedInstanceState.getInt("recKey");
        if(currentVisiblePosition < pictures.size())
        recyclerView.getLayoutManager().scrollToPosition(currentVisiblePosition);
        Log.i("TAG22", "onRestoreInstanceState " + currentVisiblePosition + " .");
    }

    @Override
    public void updateList(List<Picture> list) {
        Log.i("TAG22", "update list");
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
    protected void onPause() {
        super.onPause();
        long currentVisiblePosition;
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        Log.i("TAG22", "onPause pos - " + currentVisiblePosition);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG22", "onDestroy");
    }

}
