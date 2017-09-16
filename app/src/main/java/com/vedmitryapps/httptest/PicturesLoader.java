package com.vedmitryapps.httptest;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.vedmitryapps.httptest.api.model.Picture;

import java.io.IOException;
import java.util.List;


public class PicturesLoader extends AsyncTaskLoader<List<Picture>> {

    public PicturesLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Picture> loadInBackground() {

        try {
            return App.getApi().getPictures(App.CLIENT_ID, 1, 30)
                    .execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
