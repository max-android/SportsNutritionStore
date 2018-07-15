package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;

/**
 * Created Максим on 20.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class UserDataHolderModule {
    private Context context;

    public UserDataHolderModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public UserDataHolder provideUserDataHolder() {
        return new UserDataHolder(context);
    }
}
