package ru.exampleopit111.sportsnutritionstore.model.entities.network;

/**
 * Created Максим on 04.07.2018.
 * Copyright © Max
 */
public class OrderRequestEntity {
    private String type_payment;
    private String name;
    private String phone;
    private String comment;
    private String price;
    private String number_card;
    private UserAddress userAddress;
    private String importation_date;

    public OrderRequestEntity(String type_payment, String name, String phone, String comment, String price) {
        this.type_payment = type_payment;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.price = price;
    }

    public OrderRequestEntity(String type_payment, String name, String phone, String comment, String price, UserAddress userAddress, String importation_date) {
        this.type_payment = type_payment;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.price = price;
        this.userAddress = userAddress;
        this.importation_date = importation_date;
    }

    public OrderRequestEntity(String type_payment, String name, String phone, String comment, String price, String number_card, UserAddress userAddress, String importation_date) {
        this.type_payment = type_payment;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.price = price;
        this.number_card = number_card;
        this.userAddress = userAddress;
        this.importation_date = importation_date;
    }

    public OrderRequestEntity(String type_payment, String name, String phone, String comment, String price, String number_card) {
        this.type_payment = type_payment;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
        this.price = price;
        this.number_card = number_card;
    }


    public String getType_payment() {
        return type_payment;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getComment() {
        return comment;
    }

    public String getPrice() {
        return price;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public String getNumber_card() {
        return number_card;
    }

    public String getImportation_date() {
        return importation_date;
    }
}
