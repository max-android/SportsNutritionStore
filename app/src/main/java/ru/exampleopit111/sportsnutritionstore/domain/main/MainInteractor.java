package ru.exampleopit111.sportsnutritionstore.domain.main;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.internal_storage.InternalFileManager;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;

/**
 * Created Максим on 20.06.2018.
 * Copyright © Max
 */
public class MainInteractor {

    @Inject
    DataBaseRepository dataBaseRepository;

    @Inject
    ProfileRepository profileRepository;

    public MainInteractor(DataBaseRepository dataBaseRepository, ProfileRepository profileRepository) {
        this.dataBaseRepository = dataBaseRepository;
        this.profileRepository = profileRepository;
    }

    public InternalFileManager profile() {
        return profileRepository.profile();
    }

    public UserDataHolder user() {
        return profileRepository.userDataHolder();
    }

    public Flowable<List<Product>> basket() {
        return dataBaseRepository.isUpdateBasket()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Product>> favorite() {
        return dataBaseRepository.isUpdateFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
