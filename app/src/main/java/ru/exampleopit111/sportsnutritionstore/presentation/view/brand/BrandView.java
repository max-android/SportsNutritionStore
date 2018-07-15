package ru.exampleopit111.sportsnutritionstore.presentation.view.brand;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public interface BrandView {
    void showProducts(List<Product> products);

    void showMessage(String message);

    void showProgress();

    void removeProgress();

    void showError(String error);
}
