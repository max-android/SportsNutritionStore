package ru.exampleopit111.sportsnutritionstore.domain.favorite;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class FavoriteInteractor {

    @Inject
    DataBaseRepository dataBaseRepository;

    public FavoriteInteractor(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    public Single<List<Product>> favorites() {
        return dataBaseRepository.getFavoriteProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateFavorite(String name, boolean favorite) {
        dataBaseRepository.updateFavorite(name, favorite);
    }

    public void updateBasket(String name, boolean favorite) {
        dataBaseRepository.updateBasket(name, favorite);
    }

}
