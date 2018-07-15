package ru.exampleopit111.sportsnutritionstore.model.network;

import io.reactivex.Flowable;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class AdvertisingService {

    //здесь будет описан интерфейс для взаимодействия с сервером

    public Flowable<String> primaryRequest() {
        return Flowable.just(Constants.WEB_URL);
    }

    public Flowable<String> aboutappRequest() {
        return Flowable.just(Constants.ABOUT_APP);
    }
}
