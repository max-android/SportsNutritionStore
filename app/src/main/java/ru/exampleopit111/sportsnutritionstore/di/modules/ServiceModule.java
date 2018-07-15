package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.exampleopit111.sportsnutritionstore.model.network.AdvertisingService;
import ru.exampleopit111.sportsnutritionstore.model.network.MapService;
import ru.exampleopit111.sportsnutritionstore.model.network.SportsNutritionService;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class ServiceModule {

    @Provides
    @Singleton
    public AdvertisingService provideAdvertisingService() {
        return new AdvertisingService();
    }

    @Provides
    @Singleton
    SportsNutritionService provideSportsNutritionService() {
        return new SportsNutritionService();
    }

    @Provides
    @Singleton
    public MapService provideMapService(@NonNull Retrofit retrofit) {
        return retrofit.create(MapService.class);
    }
}
