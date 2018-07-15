package ru.exampleopit111.sportsnutritionstore.model.repository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.network.AdvertisingService;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class AdvertisingRepository {

    @Inject
    AdvertisingService service;

    public AdvertisingRepository(AdvertisingService service) {
        this.service = service;
    }

    public Flowable<String> getPrimaryData() {
        return service.primaryRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<String> getAboutAppData() {
        return service.aboutappRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
