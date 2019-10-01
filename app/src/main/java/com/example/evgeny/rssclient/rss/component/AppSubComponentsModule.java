package com.example.evgeny.rssclient.rss.component;

import com.example.evgeny.rssclient.rss.RssFeedActivity;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {RssActivityComponent.class})
public class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(RssFeedActivity.class)
    ActivityComponentBuilder provideRssFeedViewBuilder(RssActivityComponent.Builder builder) {
        return builder;
    }
}
