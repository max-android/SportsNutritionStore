package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class CiceroneModule {

    @Provides
    @Singleton
    public Cicerone<Router> provideCicerone() {
        return Cicerone.create();
    }

    @Provides
    @Singleton
    public NavigatorHolder getNavigatorHolder(@NonNull Cicerone<Router> cicerone) {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    public Router getRouter(@NonNull Cicerone<Router> cicerone) {
        return cicerone.getRouter();
    }

}
