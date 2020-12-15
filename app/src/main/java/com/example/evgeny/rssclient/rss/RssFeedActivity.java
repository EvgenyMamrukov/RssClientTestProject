package com.example.evgeny.rssclient.rss;

import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.evgeny.rssclient.R;
import com.example.evgeny.rssclient.rss.app.RssClientApplication;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.module.RssFeedActivityModule;
import com.example.evgeny.rssclient.rss.view.RssFeedViewController;

import javax.inject.Inject;

/**
 * Активити ленты новостей. Выступает в качестве архитектурного компонента View.
 */
public class RssFeedActivity extends AppCompatActivity {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    RssFeedContract.RssFeedPresenter rssFeedPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        RssClientApplication.getApp(this)
                .getComponentsHolder()
                .getActivityComponent(getClass(), new RssFeedActivityModule())
                .inject(this);

        RssFeedViewController rssFeedViewController = new RssFeedViewController(this, rssFeedPresenter);
        getLifecycle().addObserver(rssFeedViewController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            //Сценарий завершения работы активити (не временного уничтожения и пересоздания!)
            RssClientApplication.getApp(this)
                    .getComponentsHolder()
                    .releaseActivityComponent(getClass());
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}