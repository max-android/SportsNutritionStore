package ru.exampleopit111.sportsnutritionstore.domain.self_delivery_method;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;

/**
 * Created Максим on 04.07.2018.
 * Copyright © Max
 */
public class SelfDeliveryMethodInteractor {

    @Inject
    ProfileRepository profileRepository;

    public SelfDeliveryMethodInteractor(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserDataHolder user() {
        return profileRepository.userDataHolder();
    }
}
