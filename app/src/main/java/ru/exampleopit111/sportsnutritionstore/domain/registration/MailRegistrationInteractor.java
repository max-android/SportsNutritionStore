package ru.exampleopit111.sportsnutritionstore.domain.registration;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.internal_storage.InternalFileManager;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;

/**
 * Created Максим on 19.06.2018.
 * Copyright © Max
 */
public class MailRegistrationInteractor {

    @Inject
    ProfileRepository repository;

    public MailRegistrationInteractor(ProfileRepository repository) {
        this.repository = repository;
    }

    public InternalFileManager profile() {
        return repository.profile();
    }

    public UserDataHolder user() {
        return repository.userDataHolder();
    }
}
