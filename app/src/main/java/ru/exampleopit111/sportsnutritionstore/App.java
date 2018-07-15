package ru.exampleopit111.sportsnutritionstore;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import ru.exampleopit111.sportsnutritionstore.di.components.AppComponent;
import ru.exampleopit111.sportsnutritionstore.di.components.DaggerAppComponent;
import ru.exampleopit111.sportsnutritionstore.di.modules.CiceroneModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.DatabaseModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.FileModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.GlideModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.InteractorModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.NetInspectorModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.NetworkModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.PermissionModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.PresenterModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.RepositoryModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.ResourceModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.ServiceModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.ThemeInstanceModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.UserDataHolderModule;


public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .glideModule(new GlideModule(this))
                .databaseModule(new DatabaseModule(this))
                .netInspectorModule(new NetInspectorModule(this))
                .resourceModule(new ResourceModule(this))
                .ciceroneModule(new CiceroneModule())
                .userDataHolderModule(new UserDataHolderModule(this))
                .permissionModule(new PermissionModule())
                .fileModule(new FileModule(this))
                .serviceModule(new ServiceModule())
                .networkModule(new NetworkModule())
                .themeInstanceModule(new ThemeInstanceModule(this))
                .repositoryModule(new RepositoryModule())
                .interactorModule(new InteractorModule())
                .presenterModule(new PresenterModule())
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }
}
