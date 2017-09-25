package com.vedmitryapps.httptest.presenters;

import com.vedmitryapps.httptest.api.model.Picture;

import java.util.List;


public interface MessageView {

    void updateList(List<Picture> list);
    void showError();
    void showBigPicture(int pos);
}
