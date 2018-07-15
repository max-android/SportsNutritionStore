package ru.exampleopit111.sportsnutritionstore.domain.about_app;

import javax.inject.Inject;

import io.reactivex.Flowable;
import ru.exampleopit111.sportsnutritionstore.model.repository.AdvertisingRepository;

/**
 * Created Максим on 24.06.2018.
 * Copyright © Max
 */
public class AboutAppInteractor {

    @Inject
    AdvertisingRepository repository;

    public AboutAppInteractor(AdvertisingRepository repository) {
        this.repository = repository;
    }

    public Flowable<String> getData() {
        return repository.getAboutAppData();
    }
}
