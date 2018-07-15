package ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.domain.registration.MailRegistrationInteractor;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.registration.MailRegistrationView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;

/**
 * Created Максим on 18.06.2018.
 * Copyright © Max
 */
public class MailRegistrationPresenter extends BasePresenter<MailRegistrationView> {

    @Inject
    MailRegistrationInteractor interactor;
    private Handler handler;

    public MailRegistrationPresenter(MailRegistrationInteractor interactor) {
        this.interactor = interactor;
        handler = new Handler(Looper.getMainLooper());
    }

    @SuppressLint("CheckResult")
    public void writeProfileImage(String url) {
        Completable.fromCallable(
                () -> {
                    interactor.profile().writeFile(url);
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(Constants.LOG, "профайл записан"),
                        (error) -> Log.d(Constants.LOG, "ошибка записи"));
    }

    public void getImageProfileUrl() {
        new Thread(
                () -> {
                    String url = interactor.profile().readFile();
                    handler.post(() -> {
                        view.setImageProfile(url);
                    });
                }
        ).start();
    }

    public boolean existProfileImage() {
        return interactor.profile().checkExistFile();
    }

    @SuppressLint("CheckResult")
    public void deleteProfileImage() {
        Completable.fromCallable(
                () -> {
                    interactor.profile().deleteFile();
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(Constants.LOG, "файл удален"),
                        (error) -> Log.d(Constants.LOG, "ошибка удаления"));
    }

    public void saveProfile(String name) {
        interactor.user().setDataUser(Constants.USER_NAME_HOLDER, name);
    }

    public void getProfile() {
        view.setNameProfile(interactor.user()
                .getPreferences()
                .getString(Constants.USER_NAME_HOLDER, Constants.DEFAULT_NAME));
    }
}
