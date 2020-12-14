package com.example.evgeny.rssclient.rss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.evgeny.rssclient.R;
import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.app.RssClientApplication;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.module.RssFeedActivityModule;
import com.example.evgeny.rssclient.rss.view.EndlessRecyclerViewScrollListener;
import com.example.evgeny.rssclient.rss.view.adapter.RssFeedAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Активити ленты новостей. Выступает в качестве архитектурного компонента View.
 */
public class RssFeedActivity extends AppCompatActivity implements RssFeedContract.RssFeedView {

    @Inject
    RssFeedContract.RssFeedPresenter rssFeedPresenter;

    private RecyclerView rssFeedList;
    private LinearLayoutManager layoutManager;
    private RssFeedAdapter rssFeedAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        RssClientApplication.getApp(this)
                .getComponentsHolder()
                .getActivityComponent(getClass(), new RssFeedActivityModule())
                .inject(this);

        rssFeedPresenter.attachView(this);

        rssFeedList = findViewById(R.id.rss_feed_list);
        layoutManager = new LinearLayoutManager(this);
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

    @Override
    public void onStart() {
        super.onStart();
        rssFeedPresenter.provideRssFeedContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rssFeedPresenter.detachView();

        if (isFinishing()) {
            //Сценарий завершения работы активити (не временного уничтожения и пересоздания!)
            rssFeedPresenter.destroy();
            RssClientApplication.getApp(this)
                    .getComponentsHolder()
                    .releaseActivityComponent(getClass());
        }
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
        Toast.makeText(this, messageText, Toast.LENGTH_LONG).show();
    }
}