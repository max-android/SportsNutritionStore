package ru.exampleopit111.sportsnutritionstore.presentation.view.category;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public interface CategoryView {
    void showCategories(List<ProductCategory> categories);

    void showError(String error);

    void showProgress();

    void removeProgress();
}
