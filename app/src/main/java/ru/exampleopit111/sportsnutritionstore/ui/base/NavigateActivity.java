package ru.exampleopit111.sportsnutritionstore.ui.base;

import android.os.Bundle;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.App;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public abstract class NavigateActivity extends DrawerActivity {

    @Inject
    NavigatorHolder navigatorHolder;

    /**
     * @return навигатор, реализуемый конкретной активностью
     */
    protected abstract Navigator getNavigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().injectNavigateActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(getNavigator());
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }
}
