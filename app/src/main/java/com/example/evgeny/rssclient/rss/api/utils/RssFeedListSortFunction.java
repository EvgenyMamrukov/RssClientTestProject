package com.example.evgeny.rssclient.rss.api.utils;

import android.support.annotation.NonNull;

import com.example.evgeny.rssclient.rss.api.model.RssFeed;
import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.model.DateSort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RssFeedListSortFunction implements Function<List<RssItem>, Single<List<RssItem>>> {

    private static final String SERVER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";

    private DateSort dateSort;

    public RssFeedListSortFunction(@NonNull DateSort dateSort) {
        this.dateSort = dateSort;
    }

    @Override
    public Single<List<RssItem>> apply(List<RssItem> rssItemList) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT);
        Collections.sort(rssItemList, new Comparator<RssItem>() {
            @Override
            public int compare(RssItem rssItem1, RssItem rssItem2) {
                try {
                    Date rssDate1 = dateFormat.parse(rssItem1.pubDate);
                    Date rssDate2 = dateFormat.parse(rssItem2.pubDate);

                    if (dateSort == DateSort.FROM_OLD_TO_NEW) {
                        return rssDate1.compareTo(rssDate2);
                    } else {
                        return rssDate2.compareTo(rssDate1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();

                    return 0;
                }
            }
        });

        return Single.just(rssItemList);
    }
}
