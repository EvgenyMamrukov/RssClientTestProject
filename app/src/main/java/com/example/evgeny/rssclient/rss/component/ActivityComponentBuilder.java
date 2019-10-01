package com.example.evgeny.rssclient.rss.component;

import com.example.evgeny.rssclient.rss.module.ActivityModule;

public interface ActivityComponentBuilder<C extends ActivityComponent, M extends ActivityModule>   {

    C build();

    ActivityComponentBuilder<C,M> module(M module);
}
