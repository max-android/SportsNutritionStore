package ru.exampleopit111.sportsnutritionstore.model.entities.network;

/**
 * Created Максим on 04.07.2018.
 * Copyright © Max
 */
public class UserAddress {

    private String street;
    private int house;
    private int apartment;

    public UserAddress(String street, int house, int apartment) {
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getApartment() {
        return apartment;
    }
}
