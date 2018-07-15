package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.model.internal_storage.InternalFileManager;

/**
 * Created Максим on 19.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class FileModule {

    private Context context;

    public FileModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public InternalFileManager provideInternalFileManager() {
        return new InternalFileManager(context);
    }
}
