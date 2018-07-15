package ru.exampleopit111.sportsnutritionstore.presentation.view.basket;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public interface BasketView {
    void showProducts(List<Product> products);

    void showMessage(String message);

    void showProgress();

    void removeProgress();

    void showError(String error);

    void noRegistration();
}
