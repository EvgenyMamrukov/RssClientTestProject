package com.example.evgeny.rssclient.rss.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.evgeny.rssclient.rss.qualifier.ApplicationContext;
import com.example.evgeny.rssclient.rss.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(@NonNull Context context){
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext(){
        return context;
    }
}
