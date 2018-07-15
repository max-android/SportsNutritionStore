package ru.exampleopit111.sportsnutritionstore.domain.search;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;

/**
 * Created Максим on 06.07.2018.
 * Copyright © Max
 */
public class SearchInteractor {

    @Inject
    DataBaseRepository repository;

    public SearchInteractor(DataBaseRepository repository) {
        this.repository = repository;
    }

    public Single<List<Product>> products() {
        return repository.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Product>> search(String search) {
        return repository.searchProducts(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateFavorite(String name, boolean favorite) {
        repository.updateFavorite(name, favorite);
    }

    public void updateBasket(String name, boolean favorite) {
        repository.updateBasket(name, favorite);
    }
}
