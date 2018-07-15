package ru.exampleopit111.sportsnutritionstore.presentation.view.main;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 07.06.2018.
 * Copyright © Max
 */
public interface MainView {
    void setCountOrdersBusket(List<Product> products);

    void setCountFavoriteProducts(List<Product> products);

    void setProfile(String email, String phone, String url);

}
