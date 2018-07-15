package ru.exampleopit111.sportsnutritionstore.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;


/**
 * Created by Максим on 06.06.2018.
 */


@Database(entities = {Product.class}, version = 1)
public abstract class AppBase extends RoomDatabase {
    public abstract StoreDAO getStoreDAO();
}