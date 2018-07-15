package ru.exampleopit111.sportsnutritionstore.model.repository;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.internal_storage.InternalFileManager;

/**
 * Created Максим on 19.06.2018.
 * Copyright © Max
 */
public class ProfileRepository {

    @Inject
    InternalFileManager fileManager;
    @Inject
    UserDataHolder holder;

    public ProfileRepository(InternalFileManager fileManager,UserDataHolder holder) {
        this.fileManager = fileManager;
        this.holder = holder;
    }

    public InternalFileManager profile(){
        return fileManager;
    }

    public UserDataHolder userDataHolder(){
        return holder;
    }
}
