package ru.exampleopit111.sportsnutritionstore.presentation.view.products_category;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public interface ProductsCategoryView {
    void showProducts(List<Product> products);

    void showMessage(String message);

    void showProgress();

    void removeProgress();

    void showError(String error);

}
