package com.example.evgeny.rssclient.rss.api.service;

import com.example.evgeny.rssclient.rss.api.model.RssFeed;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RssApiService {
    @GET("https://habr.com/ru/rss/hubs/all/")
    Single<RssFeed> getHabrRssFeed();

    @GET("https://meduza.io/rss/all")
    Single<RssFeed> getMedusaRssFeed();
}
