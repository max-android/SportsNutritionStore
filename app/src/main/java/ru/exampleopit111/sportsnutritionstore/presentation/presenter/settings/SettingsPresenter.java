package ru.exampleopit111.sportsnutritionstore.presentation.presenter.settings;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.settings.SettingsInteractor;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.settings.SettingsView;

/**
 * Created Максим on 07.07.2018.
 * Copyright © Max
 */
public class SettingsPresenter extends BasePresenter<SettingsView> {

    @Inject SettingsInteractor interactor;
    @Inject ResourceManager resourceManager;

    public SettingsPresenter(SettingsInteractor interactor, ResourceManager resourceManager) {
        this.interactor = interactor;
        this.resourceManager = resourceManager;
    }

    public void onCleanHistorySwitchChecked(boolean isChecked){
        if (isChecked){
            cleanHistoryBasketAndFavorite();
        }
    }

    @SuppressLint("CheckResult")
    private void cleanHistoryBasketAndFavorite(){
        Completable.fromCallable(
                                () -> {
                                    interactor.updateBasket();
                                    interactor.updateFavorite();
                                    return null;
                                }
                        ).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> view.showMessage(resourceManager.getString(R.string.successful_clean_history)),
                                        (error) -> view.showError(error.getMessage()));
    }

}
