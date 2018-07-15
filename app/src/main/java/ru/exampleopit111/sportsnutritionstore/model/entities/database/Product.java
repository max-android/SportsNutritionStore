package ru.exampleopit111.sportsnutritionstore.model.entities.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
@Entity(tableName = "storetable")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String category;
    private String producer;
    private String description;
    private String applying;
    private String contraindications;
    private int availability;
    private int price;
    private String address;
    private String image;
    private boolean favorite;
    private boolean basket;
    private int currentOrder;


    public Product(String name, String category, String producer, String description, String applying, String contraindications, int availability, int price, String address, String image, boolean favorite, boolean basket, int currentOrder) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.description = description;
        this.applying = applying;
        this.contraindications = contraindications;
        this.availability = availability;
        this.price = price;
        this.address = address;
        this.image = image;
        this.favorite = favorite;
        this.basket = basket;
        this.currentOrder = currentOrder;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getProducer() {
        return producer;
    }

    public String getDescription() {
        return description;
    }

    public String getApplying() {
        return applying;
    }

    public String getContraindications() {
        return contraindications;
    }

    public int getAvailability() {
        return availability;
    }

    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isBasket() {
        return basket;
    }

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApplying(String applying) {
        this.applying = applying;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setBasket(boolean basket) {
        this.basket = basket;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }
}
