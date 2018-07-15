package ru.exampleopit111.sportsnutritionstore.model.entities.network;

import java.io.Serializable;

/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public class Brand implements Serializable {

    private String brand;
    private String url;

    public Brand(String brand) {
        this.brand = brand;
    }

    public Brand(String brand, String url) {
        this.brand = brand;
        this.url = url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
