package com.vedmitryapps.httptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.vedmitryapps.httptest.api.model.Picture;
import com.vedmitryapps.httptest.presenters.MessageView;

import java.util.List;

public class PictureLoaderCallbacks implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Picture>> {

    Context context;
    private MessageView view;


    public PictureLoaderCallbacks(Context context, MessageView view ) {
        this.context = context;
        this.view = view;
    }

    @Override
    public Loader<List<Picture>> onCreateLoader(int i, Bundle bundle) {
        if(i == R.id.posts_recycle_view) {
            return new PicturesLoader(context);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Picture>> loader, List<Picture> pictures) {
        if(loader.getId() == R.id.posts_recycle_view){
            view.updateList(pictures);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Picture>> loader) {

    }
}
 