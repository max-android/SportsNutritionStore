package ru.exampleopit111.sportsnutritionstore.domain.settings;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;

/**
 * Created Максим on 07.07.2018.
 * Copyright © Max
 */
public class SettingsInteractor {

    @Inject
    DataBaseRepository repository;

    public SettingsInteractor(DataBaseRepository repository) {
        this.repository = repository;
    }

    public void updateFavorite() {
        repository.updateAllFavoriteStatus();
    }

    public void updateBasket() {
        repository.updateAllBasketStatus();
    }

}
