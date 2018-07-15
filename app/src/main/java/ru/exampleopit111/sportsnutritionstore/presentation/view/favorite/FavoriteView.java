package ru.exampleopit111.sportsnutritionstore.presentation.view.favorite;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public interface FavoriteView {
    void showProducts(List<Product> products);

    void showProgress();

    void removeProgress();

    void showError(String error);
}
