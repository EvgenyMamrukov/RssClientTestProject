package com.example.evgeny.rssclient.rss.repository;

import com.example.evgeny.rssclient.rss.api.model.RssFeed;
import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.example.evgeny.rssclient.rss.api.service.RssApiService;
import com.example.evgeny.rssclient.rss.api.utils.RssFeedListPageFunction;
import com.example.evgeny.rssclient.rss.api.utils.RssFeedListSortFunction;
import com.example.evgeny.rssclient.rss.contract.RssFeedContract;
import com.example.evgeny.rssclient.rss.model.DateSort;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Класс отвечающий за загрузку данных из сети и их кэширование. Выступает в качестве
 * архитектурного компонента Model.
 */
public class RssFeedRepository implements RssFeedContract.RssFeedRepository {

    //Ссылка на апи сервис
    private RssApiService apiService;
    //Кэш данных
    private List<RssItem> rssFeedCache;

    public RssFeedRepository(RssApiService apiService) {
        this.apiService = apiService;
        rssFeedCache = new ArrayList<>();
    }

    /**
     * Метод, получающий данные из кэша в случае, если данные были загружены ранее, иначе вызываются
     * методы API информационных порталов. Загруженные данные кэшируются.
     *
     * @param page Индекс начального элемента данных.
     * @param perPage Количество элементов.
     * @param dateSort Тип сортировки по дате публикации (от новых к старым / от старых к новым).
     * @return Возвращает сингл, сгенерирующий полученные данные.
     */
    @Override
    public Single<List<RssItem>> loadRssFeedContent(final int page, final int perPage, DateSort dateSort) {

        //Сингл, отвечающий за предоставление данных методов api
        Single<List<RssItem>> apiRssFeedSingle = loadNewRssFeedContent(page, perPage, dateSort);

        //Сингл, отвечающий за предоставление кэшированных данных
        Single<List<RssItem>> cacheRssFeedSingle = Single.just(rssFeedCache);

        //Сингл вернет кэшированные данные, если он не пустой, иначе вернет данные результата запроса
        Single<List<RssItem>> resultRssFeedSingle = Single.concat(cacheRssFeedSingle, apiRssFeedSingle)
                .filter(new Predicate<List<RssItem>>() {
                    @Override
                    public boolean test(List<RssItem> rssItems) throws Exception {
                        return !rssItems.isEmpty();
                    }
                })
                .firstOrError();

        return resultRssFeedSingle;
    }


    /**
     * Метод, загружающий новую страницу данных, вызывая методы API информационных порталов.
     * Полученные данные кэшируются.
     *
     * @param page Индекс начального элемента данных.
     * @param perPage Количество элементов.
     * @param dateSort Тип сортировки по дате публикации (от новых к старым / от старых к новым).
     * @return Возвращает сингл, сгенерирующий полученные данные.
     */
    @Override
    public Single<List<RssItem>> loadNewRssFeedContent(
            final int page,
            final int perPage,
            DateSort dateSort
    ) {
        //Сингл, отвечающий за загрузку данных с habr
        Single<RssFeed> habrRssFeedSingle = apiService.getHabrRssFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //Сингл, отвечающий за загрузку данных с medusa
        Single<RssFeed> medusaRssFeedSingle = apiService.getMedusaRssFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //Сингл, отвечающий за объединение результатов запросов
        Single<List<RssItem>> apiRssFeedSingle = Single.zip(habrRssFeedSingle, medusaRssFeedSingle, new BiFunction<RssFeed, RssFeed, List<RssItem>>() {
            @Override
            public List<RssItem> apply(RssFeed rssFeedFirst, RssFeed rssFeedSecond) throws Exception {
                List<RssItem> rssItemList = new ArrayList<>();

                if (rssFeedFirst.channel.item != null && !rssFeedFirst.channel.item.isEmpty()) {
                    rssItemList.addAll(rssFeedFirst.channel.item);
                }

                if (rssFeedSecond.channel.item != null && !rssFeedSecond.channel.item.isEmpty()) {
                    rssItemList.addAll(rssFeedSecond.channel.item);
                }

                return rssItemList;
            }
        })
                //Сортировка полученных данных по дате публикации
                .flatMap(new RssFeedListSortFunction(dateSort))
                //Получение нужной страницы (Пагинация делается искуственно,
                //так как не удалось понять как у метода апи вытащить конкретную страницу)
                .flatMap(new RssFeedListPageFunction(page, perPage))
                //Кэширование результата запроса в случае успеха
                .doOnSuccess(new Consumer<List<RssItem>>() {
                    @Override
                    public void accept(List<RssItem> rssItems) throws Exception {
                        rssFeedCache.addAll(rssItems);
                    }
                });

        return apiRssFeedSingle;
    }
}
