package com.example.evgeny.rssclient.rss.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.view.adapter.RssFeedAdapter;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class RssFeedViewController implements RssFeedContract.RssFeedView, LifecycleObserver {

    private RecyclerView rssFeedList;
    private final RssFeedContract.RssFeedPresenter rssFeedPresenter;
    private LinearLayoutManager layoutManager;
    private RssFeedAdapter rssFeedAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    public RssFeedViewController(@NonNull RecyclerView rssFeedList, @NonNull RssFeedContract.RssFeedPresenter presenter) {
        this.rssFeedList = rssFeedList;
        this.rssFeedPresenter = presenter;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        this.rssFeedPresenter.attachView(this);

        if (rssFeedList != null) {
            layoutManager = new LinearLayoutManager(rssFeedList.getContext());
            rssFeedAdapter = new RssFeedAdapter();
            rssFeedList.setLayoutManager(layoutManager);
            rssFeedList.setAdapter(rssFeedAdapter);
            scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    rssFeedPresenter.provideNewRssFeedContent();
                }
            };
            rssFeedList.addOnScrollListener(scrollListener);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        rssFeedPresenter.provideRssFeedContent();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        rssFeedPresenter.detachView();
    }

    /**
     * Метод отображения элементов списка новостей.
     * @param rssItemList Список rss данных, полученных от презентера.
     */
    @Override
    public void showRssFeedContent(List<RssItem> rssItemList) {
        rssFeedAdapter.setRssFeedList(rssItemList);
    }

    /**
     * Метод отображения элементов новой страницы новостей.
     * @param newRssItemList Список rss данных, полученных от презентера.
     */
    @Override
    public void showNewRssFeedContent(List<RssItem> newRssItemList) {
        rssFeedAdapter.addRssFeedList(newRssItemList);
    }

    @Override
    public void showMessage(String messageText) {
        Toast.makeText(rssFeedList.getContext(), messageText, Toast.LENGTH_LONG).show();
    }
}
