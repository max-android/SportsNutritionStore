package ru.exampleopit111.sportsnutritionstore.model.entities.network;

import java.io.Serializable;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class ProductCategory implements Serializable {

    private String category;

    public ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
