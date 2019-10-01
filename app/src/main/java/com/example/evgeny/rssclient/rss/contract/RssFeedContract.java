package com.example.evgeny.rssclient.rss.contract;

import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.model.DateSort;
import com.example.evgeny.rssclient.rss.presenter.Presenter;
import com.example.evgeny.rssclient.rss.repository.BaseRepository;
import com.example.evgeny.rssclient.rss.view.BaseView;

import java.util.List;

import io.reactivex.Single;

/**
 * Контракт для организации функционала формирования и отображения ленты новостей.
 */
public interface RssFeedContract {

    //Контракт View.
    interface RssFeedView extends BaseView {
        void showRssFeedContent(List<RssItem> rssItemList);

        void showNewRssFeedContent(List<RssItem> newRssItemList);

        void showMessage(String messageText);
    }

    //Контракт Presenter.
    interface RssFeedPresenter extends Presenter<RssFeedView> {
        void provideRssFeedContent();

        void provideNewRssFeedContent();
    }

    //Контракт Model.
    interface RssFeedRepository extends BaseRepository {
        Single<List<RssItem>> loadRssFeedContent(int page, int perPage, DateSort dateSort);

        Single<List<RssItem>> loadNewRssFeedContent(int page, int perPage, DateSort dateSort);
    }
}
