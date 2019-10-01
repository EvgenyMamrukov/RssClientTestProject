package com.example.evgeny.rssclient.rss.app;

import android.app.Application;
import android.content.Context;
import com.example.evgeny.rssclient.rss.component.ComponentsHolder;

public class RssClientApplication extends Application {

    private ComponentsHolder componentsHolder;

    public static RssClientApplication getApp(Context context) {
        return (RssClientApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        componentsHolder = new ComponentsHolder(this);
        componentsHolder.init();
    }

    public ComponentsHolder getComponentsHolder() {
        return componentsHolder;
    }
}
