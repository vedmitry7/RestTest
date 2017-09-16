package com.vedmitryapps.httptest.presenters;

import com.vedmitryapps.httptest.App;
import com.vedmitryapps.httptest.api.model.Picture;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Presenter implements MessagePresenter {

    private MessageView view;

    public Presenter(MessageView view) {
        this.view = view;
    }


    void loadNextPictures( int page){
        System.out.println("scroll - loadNextPictures " + page);

        Call<List<Picture>> call = App.getApi().getPictures(App.CLIENT_ID, page, 30);
        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                view.updateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                view.showError();
            }
        });
    }

    @Override
    public void onScrollToEnd(int page) {
        loadNextPictures(page);
    }

    @Override
    public void onItemClick(int pos) {
        view.showBigPicture(pos);
    }
}
