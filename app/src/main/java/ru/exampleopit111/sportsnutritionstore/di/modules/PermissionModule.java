package ru.exampleopit111.sportsnutritionstore.di.modules;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.ui.common.LocationPermission;
import ru.exampleopit111.sportsnutritionstore.ui.common.WritePermission;

/**
 * Created by Максим on 19.06.2018.
 */


@Module
@Singleton
public class PermissionModule {


    @Provides
    @Singleton
    public WritePermission provideWritePermission() {
        WritePermission writePermission = new WritePermission();
        return writePermission;
    }

    @Provides
    @Singleton
    public LocationPermission provideLocationPermission() {
        LocationPermission locationPermission = new LocationPermission();
        return locationPermission;
    }
}
