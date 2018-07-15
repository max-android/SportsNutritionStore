package ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration;

import android.os.CountDownTimer;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.domain.registration.PhoneRegistrationInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.UserEntity;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.registration.PhoneRegistrationView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 15.06.2018.
 * Copyright © Max
 */
public class PhoneRegistrationPresenter extends BasePresenter<PhoneRegistrationView> {

    private final static long TIMER_INTERVAL = 1000;
    private final static long TIMER_LIMIT = 10000;
    private TimerAwaitTask counter;

    @Inject
    PhoneRegistrationInteractor interactor;
    @Inject
    Router router;

    public PhoneRegistrationPresenter(PhoneRegistrationInteractor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    public void onSendCodeSms(String phone) {
        //здесь будет запрос на сервер для получения смс из телефона
    }

    public void onMailRegisterClicked(String phone, String code) {
        interactor.userDataHolder().setDataUser(Constants.KEY_PHONE_HOLDER, phone);
        router.replaceScreen(Screens.MAIL_REG_FRAGMENT, new UserEntity(phone));
    }

    public void startTimer() {
        counter = new TimerAwaitTask(TIMER_LIMIT, TIMER_INTERVAL);
        counter.start();
        view.showTimer();
    }

    public void stopTimer() {
        if (counter != null) {
            counter.cancel();
        }
    }

    private class TimerAwaitTask extends CountDownTimer {

        TimerAwaitTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            view.setTime(String.valueOf(0));
            view.showButtonSend();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.setTime(String.valueOf(millisUntilFinished / 1000));
        }
    }
}
