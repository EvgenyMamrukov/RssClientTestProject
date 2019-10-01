package com.example.evgeny.rssclient.rss.presenter;

import com.example.evgeny.rssclient.rss.view.BaseView;

public interface Presenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

    void destroy();
}
