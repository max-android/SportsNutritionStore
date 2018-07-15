package ru.exampleopit111.sportsnutritionstore.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.ui.common.NetInspector;


@Module
@Singleton
public class NetInspectorModule {

    private Context context;

    public NetInspectorModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public NetInspector provideNetInspector() {
        return new NetInspector(context);
    }
}
