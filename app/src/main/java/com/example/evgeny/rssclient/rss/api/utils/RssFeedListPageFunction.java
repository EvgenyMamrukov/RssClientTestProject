package com.example.evgeny.rssclient.rss.api.utils;

import com.example.evgeny.rssclient.rss.api.model.RssItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RssFeedListPageFunction implements Function<List<RssItem>, Single<List<RssItem>>> {

    private final int page;
    private final int perPage;

    public RssFeedListPageFunction(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
    }

    @Override
    public Single<List<RssItem>> apply(List<RssItem> rssItems) throws Exception {
        int itemsCount = rssItems.size();
        List<RssItem> rssFeedPage = new ArrayList<>();

        int from;
        int to;

        if (page < 0) {
            from = 0;
        } else if (page < itemsCount) {
            from = page;
        } else {
            return Single.just(rssFeedPage);
        }

        if (page + perPage < 0) {
            return Single.just(rssFeedPage);
        } else if (page + perPage < itemsCount) {
            to = page + perPage;
        } else {
            to = itemsCount > 0 ? itemsCount - 1 : 0;
        }

        rssFeedPage = rssItems.subList(from, to);

        return Single.just(rssFeedPage);
    }
}
