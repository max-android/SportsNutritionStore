package ru.exampleopit111.sportsnutritionstore.model.entities.common;

import java.io.Serializable;
import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class BasketEntity implements Serializable {

    private List<Product> products;
    private String basketPrice;

    public BasketEntity(List<Product> products, String basketPrice) {
        this.products = products;
        this.basketPrice = basketPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(String basketPrice) {
        this.basketPrice = basketPrice;
    }
}
