package ru.exampleopit111.sportsnutritionstore.domain.basket;

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
public class BasketInteractor {

    @Inject
    DataBaseRepository dataBaseRepository;

    public BasketInteractor(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    public Single<List<Product>> basket() {
        return dataBaseRepository.getBasketProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateBasket(String name, int count, boolean busket) {
        dataBaseRepository.updateBasketAndCountProduct(name, count, busket);
    }
}
