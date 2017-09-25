package com.vedmitryapps.httptest;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.vedmitryapps.httptest.api.model.Picture;

import java.io.IOException;
import java.util.List;


public class PicturesLoader extends AsyncTaskLoader<List<Picture>> {

    private int count;

    public PicturesLoader(Context context, int count) {
        super(context);
        Log.i("TAG22", "PicturesLoader constructor" + count);
        this.count = count;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i("TAG22", "PictureLoader on start loading");
        forceLoad();
    }

    @Override
    public List<Picture> loadInBackground() {
        Log.i("TAG22", "PictureLoader loadInBackground");
        try {
            return App.getApi().getPictures(App.CLIENT_ID, count, 30)
                    .execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
