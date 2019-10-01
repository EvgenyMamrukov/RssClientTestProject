package com.example.evgeny.rssclient.rss.component;

import com.example.evgeny.rssclient.rss.RssFeedActivity;
import com.example.evgeny.rssclient.rss.module.RssFeedActivityModule;
import com.example.evgeny.rssclient.rss.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = RssFeedActivityModule.class)
public interface RssActivityComponent extends ActivityComponent<RssFeedActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<RssActivityComponent, RssFeedActivityModule> { }
}
