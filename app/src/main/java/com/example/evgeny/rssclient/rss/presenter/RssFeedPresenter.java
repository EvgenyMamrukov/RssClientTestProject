package com.example.evgeny.rssclient.rss.presenter;

import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.model.DateSort;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Presenter, отвечающий за обновление View и обработку UI событий экрана ленты новостей.
 */
public class RssFeedPresenter extends BasePresenter<RssFeedContract.RssFeedView>
        implements RssFeedContract.RssFeedPresenter {

    private static final int CURRENT_PAGE_DEFAULT = 0;
    private static final int PER_PAGE_DEFAULT = 10;

    private RssFeedContract.RssFeedRepository rssFeedRepository;

    private CompositeDisposable disposableList;

    private int currentPage;

    public RssFeedPresenter(RssFeedContract.RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;
        disposableList = new CompositeDisposable();
        currentPage = CURRENT_PAGE_DEFAULT;
    }

    /**
     * Метод, отвечающий за получение данных необходимых для инициализации ленты новостей.
     * Вызывается при переходе активити на передний план.
     */
    @Override
    public void provideRssFeedContent() {
        Single<List<RssItem>> rssFeedSingle = rssFeedRepository.loadRssFeedContent(
                currentPage,
                PER_PAGE_DEFAULT,
                DateSort.FROM_NEW_TO_OLD
        );

        Disposable currentDisposable = rssFeedSingle.subscribeWith(new DisposableSingleObserver<List<RssItem>>() {
            @Override
            public void onSuccess(List<RssItem> rssItems) {
                if (isViewAttached()) {
                    getView().showRssFeedContent(rssItems);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showMessage(e.getLocalizedMessage());
                }
            }
        });

        if (!currentDisposable.isDisposed()) {
            disposableList.add(currentDisposable);
        }
    }

    /**
     * Метод, отвечающий за получение данных необходимых для формирования новой страницы ленты
     * новостей. Вызывается в момент, когда лента новостей оказывается в нижнем положении.
     */
    @Override
    public void provideNewRssFeedContent() {
        currentPage = currentPage + PER_PAGE_DEFAULT;

        Single<List<RssItem>> newRssFeedSingle = rssFeedRepository.loadNewRssFeedContent(
                currentPage,
                PER_PAGE_DEFAULT,
                DateSort.FROM_NEW_TO_OLD
        );

        Disposable currentDisposable = newRssFeedSingle.subscribeWith(new DisposableSingleObserver<List<RssItem>>() {
            @Override
            public void onSuccess(List<RssItem> rssItems) {
                if (isViewAttached()) {
                    getView().showNewRssFeedContent(rssItems);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showMessage(e.getLocalizedMessage());
                }
            }
        });

        if (!currentDisposable.isDisposed()) {
            disposableList.add(currentDisposable);
        }
    }

    @Override
    public void destroy() {
        disposableList.dispose();
    }
}
