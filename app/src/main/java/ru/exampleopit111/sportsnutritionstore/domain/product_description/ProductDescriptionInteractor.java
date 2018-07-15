package ru.exampleopit111.sportsnutritionstore.domain.product_description;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class ProductDescriptionInteractor {

    @Inject
    DataBaseRepository repository;

    public ProductDescriptionInteractor(DataBaseRepository repository) {
        this.repository = repository;
    }

    public void updateBasket(String name, boolean favorite) {
        repository.updateBasket(name, favorite);
    }
}
