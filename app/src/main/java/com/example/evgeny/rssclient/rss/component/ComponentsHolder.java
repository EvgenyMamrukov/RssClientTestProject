package com.example.evgeny.rssclient.rss.component;

import android.content.Context;

import com.example.evgeny.rssclient.rss.module.ActivityModule;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ComponentsHolder {

    private final Context context;

    @Inject
    Map<Class<?>, Provider<ActivityComponentBuilder>> builders;

    private Map<Class<?>, ActivityComponent> components;
    private ApplicationComponent appComponent;

    public ComponentsHolder(Context context) {
        this.context = context;
    }

    public void init() {
        appComponent = DaggerApplicationComponent.builder().build();
        appComponent.injectComponentsHolder(this);
        components = new HashMap<>();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    public ActivityComponent getActivityComponent(Class<?> cls) {
        return getActivityComponent(cls, null);
    }

    public ActivityComponent getActivityComponent(Class<?> cls, ActivityModule module) {
        ActivityComponent component = components.get(cls);
        if (component == null) {
            ActivityComponentBuilder builder = builders.get(cls).get();
            if (module != null) {
                builder.module(module);
            }
            component = builder.build();
            components.put(cls, component);
        }
        return component;
    }

    public void releaseActivityComponent(Class<?> cls) {
        components.put(cls, null);
    }
}
