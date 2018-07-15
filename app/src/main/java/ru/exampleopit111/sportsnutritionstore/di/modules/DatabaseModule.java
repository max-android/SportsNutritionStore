package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.model.database.AppBase;

/**
 * Created by Максим on 06.06.2018.
 */

@Module
@Singleton
public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public AppBase provideDatabase() {
        return Room.databaseBuilder(context, AppBase.class, "db_store").build();
    }
}