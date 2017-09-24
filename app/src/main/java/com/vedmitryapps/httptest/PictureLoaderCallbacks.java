package com.vedmitryapps.httptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.vedmitryapps.httptest.api.model.Picture;
import com.vedmitryapps.httptest.presenters.MessageView;

import java.util.List;

public class PictureLoaderCallbacks implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Picture>> {

    private Context context;
    private int count;
    private MessageView view;


    public PictureLoaderCallbacks(Context context, MessageView view, int count ) {
        Log.i("TAG22", "PictureLoaderCallback constructor" + count);
        this.context = context;
        this.view = view;
        this.count = count;
    }

    @Override
    public Loader<List<Picture>> onCreateLoader(int i, Bundle bundle) {
        if(i == R.id.posts_recycle_view + count) {
            return new PicturesLoader(context, count);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Picture>> loader, List<Picture> pictures) {
        Log.i("TAG22", "PictureLoaderCallback load finished");
        if(loader.getId() == R.id.posts_recycle_view + count){
            view.updateList(pictures);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Picture>> loader) {

    }
}
 