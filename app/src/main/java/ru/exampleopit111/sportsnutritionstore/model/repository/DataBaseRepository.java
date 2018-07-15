package ru.exampleopit111.sportsnutritionstore.model.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exampleopit111.sportsnutritionstore.model.database.AppBase;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 05.06.2018.
 * Copyright © Max
 */
public class DataBaseRepository {

    @Inject
    AppBase database;

    public DataBaseRepository(AppBase database) {
        this.database = database;
    }

    public void writeDataIntoDB(List<Product> list) {
        database.getStoreDAO().insertProducts(list);
    }

    public Single<List<Product>> searchProductsByCategoryFromDB(String category) {
        Single<List<Product>> single = database.getStoreDAO().getProductsByCategory(category);
        return single;
    }

    public Single<List<Product>> searchProductsByBrandFromDB(String brand) {
        Single<List<Product>> single = database.getStoreDAO().getProductsByBrand(brand);
        return single;
    }

    public Single<Integer> readSizeFromBD() {
        Single<Integer> size = database.getStoreDAO().getSizeDB();
        return size;
    }

    public void updateFavorite(String name, boolean favorite) {
        database.getStoreDAO().updateProductByFavoriteStatus(name, favorite);
    }

    public void updateBasket(String name, boolean basket) {
        database.getStoreDAO().updateProductByBasketStatus(name, basket);
    }

    public Single<List<Product>> getFavoriteProducts() {
        Single<List<Product>> single = database.getStoreDAO().getFavoriteProducts(true);
        return single;
    }

    public Single<List<Product>> getBasketProducts() {
        Single<List<Product>> single = database.getStoreDAO().getBasketProducts(true);
        return single;
    }

    public void updateBasketAndCountProduct(String name, int count, boolean basket) {
        database.getStoreDAO().updateBasketStatusAndCountProduct(name, count, basket);
    }

    public Flowable<List<Product>> isUpdateBasket() {
        Flowable<List<Product>> flowable = database.getStoreDAO().isUpdateBasket(true);
        return flowable;
    }

    public Flowable<List<Product>> isUpdateFavorite() {
        Flowable<List<Product>> flowable = database.getStoreDAO().isUpdateFavorite(true);
        return flowable;
    }

    public Single<List<Product>> getProducts() {
        Single<List<Product>> single = database.getStoreDAO().getProducts();
        return single;
    }

    public Single<List<Product>> searchProducts(String search) {
        Single<List<Product>> single = database.getStoreDAO().searchProducts(search);
        return single;
    }

    public void updateAllFavoriteStatus(){
        database.getStoreDAO().updateAllFavoriteStatus(false);
    }

    public void updateAllBasketStatus(){
        database.getStoreDAO().updateAllBasketStatus(false);
    }

}
