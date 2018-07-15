package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;


/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class ResourceModule {

    private Context context;

    public ResourceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ResourceManager provideResourceManager() {
        return new ResourceManager(context);
    }
}

