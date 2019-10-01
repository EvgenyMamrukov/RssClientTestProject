package com.example.evgeny.rssclient.rss.module;

import com.example.evgeny.rssclient.rss.api.service.RssApiService;
import com.example.evgeny.rssclient.rss.api.utils.BaseUtils;
import com.example.evgeny.rssclient.rss.scope.ApplicationScope;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @ApplicationScope
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseUtils.BASE_RSS_REED_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getOkHttpClient(getHttpLoggingInterceptor()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(BaseUtils.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(BaseUtils.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @ApplicationScope
    public RssApiService provideRssApiService(Retrofit retrofit) {
        return retrofit.create(RssApiService.class);
    }
}
