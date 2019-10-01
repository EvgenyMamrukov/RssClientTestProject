package com.example.evgeny.rssclient.rss.presenter;

import com.example.evgeny.rssclient.rss.view.BaseView;

/**
 * Базовый презентер, предоставляющий возможность отслеживать наличие/отсутствие View с которой
 * идет взаимодействие. Переживает сценарии временного уничножения активити
 *(смена ориентации экрана, уничтожение по причине нехватки памяти, режим don't keep activities
 * и т.д.) Презентер является локальным синглтоном, т.е. существует на момент работы активити
 *(пока isFinished() == false).
 *
 * @param <T> Параметр определяющий View компонент с которым организовано взаимодействие.
 */
public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() { }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }
}
