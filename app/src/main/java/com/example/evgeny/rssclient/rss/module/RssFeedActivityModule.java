package com.example.evgeny.rssclient.rss.module;

import com.example.evgeny.rssclient.rss.api.service.RssApiService;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.presenter.RssFeedPresenter;
import com.example.evgeny.rssclient.rss.repository.RssFeedRepository;
import com.example.evgeny.rssclient.rss.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RssFeedActivityModule implements ActivityModule {

    @Provides
    @ActivityScope
    RssFeedContract.RssFeedPresenter provideRssFeedPresenter(RssFeedContract.RssFeedRepository rssFeedRepository) {
        return new RssFeedPresenter(rssFeedRepository);
    }

    @Provides
    @ActivityScope
    RssFeedContract.RssFeedRepository provideRssFeedRepository(RssApiService apiService) {
        return new RssFeedRepository(apiService);
    }
}
