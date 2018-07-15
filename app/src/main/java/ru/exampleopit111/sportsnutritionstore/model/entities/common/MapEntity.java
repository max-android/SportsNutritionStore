package ru.exampleopit111.sportsnutritionstore.model.entities.common;

import java.util.Objects;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class MapEntity {

    private String address;
    private double latitude;
    private double longitude;

    public MapEntity(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapEntity mapEntity = (MapEntity) o;
        return Objects.equals(address, mapEntity.address);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(address) + 17;
    }
}
