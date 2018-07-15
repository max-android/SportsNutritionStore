package ru.exampleopit111.sportsnutritionstore.domain.method_courier;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;

/**
 * Created Максим on 05.07.2018.
 * Copyright © Max
 */
public class MethodCourierInteractor {

    @Inject
    ProfileRepository profileRepository;

    public MethodCourierInteractor(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserDataHolder user() {
        return profileRepository.userDataHolder();
    }
}
