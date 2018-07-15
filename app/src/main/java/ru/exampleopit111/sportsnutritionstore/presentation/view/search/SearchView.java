package ru.exampleopit111.sportsnutritionstore.presentation.view.search;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 06.07.2018.
 * Copyright © Max
 */
public interface SearchView {
    void showProducts(List<Product> products);

    void showMessage(String message);

    void showProgress();

    void removeProgress();

    void showError(String error);
}
