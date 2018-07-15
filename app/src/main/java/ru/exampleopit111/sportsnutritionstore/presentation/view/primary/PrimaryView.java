package ru.exampleopit111.sportsnutritionstore.presentation.view.primary;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public interface PrimaryView {
    void showGallery(String url);

    void showError(String error);

    void showMessage(String message);

    void showProgress();

    void removeProgress();

    void showBrands(List<Brand> brands);
}
