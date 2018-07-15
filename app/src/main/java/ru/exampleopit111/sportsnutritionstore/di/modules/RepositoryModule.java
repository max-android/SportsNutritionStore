package ru.exampleopit111.sportsnutritionstore.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.model.data_holder.UserDataHolder;
import ru.exampleopit111.sportsnutritionstore.model.database.AppBase;
import ru.exampleopit111.sportsnutritionstore.model.internal_storage.InternalFileManager;
import ru.exampleopit111.sportsnutritionstore.model.network.AdvertisingService;
import ru.exampleopit111.sportsnutritionstore.model.network.MapService;
import ru.exampleopit111.sportsnutritionstore.model.network.SportsNutritionService;
import ru.exampleopit111.sportsnutritionstore.model.repository.AdvertisingRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.MapRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 15.06.2018.
 * Copyright © Max
 */

@Module
@Singleton
public class RepositoryModule {

    @Provides
    @Singleton
    public ProfileRepository provideProfileRepository(InternalFileManager fileManager,
                                                      UserDataHolder userDataHolder) {
        return new ProfileRepository(fileManager, userDataHolder);
    }

    @Provides
    @Singleton
    public AdvertisingRepository provideAdvertisingRepository(AdvertisingService service) {
        return new AdvertisingRepository(service);
    }

    @Provides
    @Singleton
    public SportsNutritionRepository provideServiceRepository(SportsNutritionService service) {
        return new SportsNutritionRepository(service);
    }

    @Provides
    @Singleton
    public DataBaseRepository provideDataBaseRepository(AppBase database) {
        return new DataBaseRepository(database);
    }

    @Provides
    @Singleton
    public MapRepository provideMapRepository(MapService mapService) {
        return new MapRepository(mapService);
    }
}
