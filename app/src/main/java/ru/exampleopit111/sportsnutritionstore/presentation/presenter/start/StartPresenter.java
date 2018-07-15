package ru.exampleopit111.sportsnutritionstore.presentation.presenter.start;

import android.os.Handler;

import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.start.StartView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;

/**
 * Created Максим on 12.07.2018.
 * Copyright © Max
 */
public class StartPresenter extends BasePresenter<StartView> {

    public StartPresenter() {
    }

    public void attach(){
        new Handler().postDelayed(() -> {
            view.showMainScreen();
        }, Constants.TIMEOUT);
    }
}
