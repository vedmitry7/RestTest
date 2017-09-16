package com.vedmitryapps.httptest.presenters;

import com.vedmitryapps.httptest.api.model.Picture;

import java.util.List;

/**
 * Created by Dmitry Vedmed on 16.09.2017.
 */

public interface MessageView {

    void updateList(List<Picture> list);
    void showError();
    void showBigPicture(int pos);
}
