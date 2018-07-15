package ru.exampleopit111.sportsnutritionstore.domain.registration;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;

/**
 * Created Максим on 15.06.2018.
 * Copyright © Max
 */
public class PhoneRegistrationInteractor {

    @Inject
    ProfileRepository repository;

    public PhoneRegistrationInteractor(ProfileRepository repository) {
        this.repository = repository;
    }

    public UserDataHolder userDataHolder() {
        return repository.userDataHolder();
    }
}
