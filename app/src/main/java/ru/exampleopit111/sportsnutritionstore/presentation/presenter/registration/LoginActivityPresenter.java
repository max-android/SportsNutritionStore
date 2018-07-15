package ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.domain.registration.PhoneRegistrationInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.UserEntity;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.registration.LoginActivityView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 16.06.2018.
 * Copyright © Max
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityView> {

    @Inject
    PhoneRegistrationInteractor interactor;
    @Inject
    Router router;

    public LoginActivityPresenter(PhoneRegistrationInteractor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    public void launchScreen() {
        if (interactor.userDataHolder().getPreferences().contains(Constants.KEY_PHONE_HOLDER)) {
            String phone = interactor.userDataHolder().getPreferences().getString(Constants.KEY_PHONE_HOLDER, Constants.DEFAULT_PHONE);
            router.replaceScreen(Screens.MAIL_REG_FRAGMENT, new UserEntity(phone));
        } else {
            router.replaceScreen(Screens.PHONE_REG_FRAGMENT);
        }
    }
}
