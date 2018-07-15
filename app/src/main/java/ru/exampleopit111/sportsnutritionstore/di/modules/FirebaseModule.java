package ru.exampleopit111.sportsnutritionstore.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 18.06.2018.
 */


@Module
@Singleton
public class FirebaseModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    @Provides
    @Singleton
    public FirebaseMessaging provideFirebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
