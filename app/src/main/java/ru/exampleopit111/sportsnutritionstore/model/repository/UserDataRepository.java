package ru.exampleopit111.sportsnutritionstore.model.repository;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;

/**
 * Created Максим on 20.06.2018.
 * Copyright © Max
 */
public class UserDataRepository {

    @Inject
    UserDataHolder holder;

    public UserDataRepository(UserDataHolder holder) {
        this.holder = holder;
    }

    public UserDataHolder userDataHolder() {
        return holder;
    }
}
