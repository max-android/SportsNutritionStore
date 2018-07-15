package ru.exampleopit111.sportsnutritionstore.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created by Максим on 22.06.2018
 */

@Dao
public interface StoreDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<Product> products);

    @Query("SELECT COUNT(*) FROM storetable")
    Single<Integer> getSizeDB();

    @Query("SELECT * FROM storetable WHERE category = :type")
    Single<List<Product>> getProductsByCategory(String type);

    @Query("SELECT * FROM storetable WHERE producer = :type")
    Single<List<Product>> getProductsByBrand(String type);

    @Query("SELECT * FROM storetable")
    Single<List<Product>> getProducts();

    @Query("UPDATE storetable SET favorite = :new_favorite WHERE name = :new_name")
    void updateProductByFavoriteStatus(
            String new_name,
            boolean new_favorite
    );

    @Query("UPDATE storetable SET basket = :new_basket WHERE name = :new_name")
    void updateProductByBasketStatus(
            String new_name,
            boolean new_basket
    );

    @Query("UPDATE storetable SET currentOrder = :new_currentOrder, basket = :new_basket  WHERE name = :new_name")
    void updateBasketStatusAndCountProduct(
            String new_name,
            int new_currentOrder,
            boolean new_basket
    );


    @Query("SELECT * FROM storetable WHERE favorite = :status_favorite")
    Single<List<Product>> getFavoriteProducts(boolean status_favorite);

    @Query("SELECT * FROM storetable WHERE basket = :status_basket")
    Single<List<Product>> getBasketProducts(boolean status_basket);

    @Query("SELECT * FROM storetable WHERE basket = :status_basket")
    Flowable<List<Product>> isUpdateBasket(boolean status_basket);

    @Query("SELECT * FROM storetable WHERE favorite = :status_favorite")
    Flowable<List<Product>> isUpdateFavorite(boolean status_favorite);

    @Query("SELECT * FROM storetable WHERE category LIKE '%' || :search || '%' OR category LIKE '%' || :search || '%' OR producer LIKE '%' || :search || '%'")
    Single<List<Product>> searchProducts(String search);


    @Query("UPDATE storetable SET favorite = :new_favorite")
    void updateAllFavoriteStatus(
            boolean new_favorite
    );

    @Query("UPDATE storetable SET basket = :new_basket")
    void updateAllBasketStatus(
            boolean new_basket
    );

}
