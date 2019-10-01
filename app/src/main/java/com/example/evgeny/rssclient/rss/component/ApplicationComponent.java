package com.example.evgeny.rssclient.rss.component;

import com.example.evgeny.rssclient.rss.module.ApplicationModule;
import com.example.evgeny.rssclient.rss.module.RetrofitModule;
import com.example.evgeny.rssclient.rss.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = { ApplicationModule.class, RetrofitModule.class, AppSubComponentsModule.class })
public interface ApplicationComponent {

    void injectComponentsHolder(ComponentsHolder componentsHolder);
}
