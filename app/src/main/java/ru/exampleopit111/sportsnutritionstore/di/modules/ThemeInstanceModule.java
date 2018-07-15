package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.ui.common.ThemeInstance;

/**
 * Created Максим on 08.07.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class ThemeInstanceModule {

    private Context context;

    public ThemeInstanceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ThemeInstance provideThemeInstance() {
        return new ThemeInstance(context);
    }
}
