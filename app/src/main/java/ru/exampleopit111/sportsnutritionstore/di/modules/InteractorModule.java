package ru.exampleopit111.sportsnutritionstore.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exampleopit111.sportsnutritionstore.domain.about_app.AboutAppInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.basket.BasketInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.brand.BrandInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.category.CategoryInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.favorite.FavoriteInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.main.MainInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.map.MapInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.method_courier.MethodCourierInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.primary.PrimaryInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.product_description.ProductDescriptionInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.products_category.ProductsCategoryInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.registration.MailRegistrationInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.registration.PhoneRegistrationInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.search.SearchInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.self_delivery_method.SelfDeliveryMethodInteractor;
import ru.exampleopit111.sportsnutritionstore.domain.settings.SettingsInteractor;
import ru.exampleopit111.sportsnutritionstore.model.repository.AdvertisingRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.MapRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.ProfileRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 16.06.2018.
 * Copyright © Max
 */
@Module
@Singleton
public class InteractorModule {

    @Provides
    @Singleton
    public PhoneRegistrationInteractor providePhoneRegInteractor(ProfileRepository repository) {
        return new PhoneRegistrationInteractor(repository);
    }

    @Provides
    @Singleton
    public MailRegistrationInteractor provideMailRegistrationInteractor(ProfileRepository repository) {
        return new MailRegistrationInteractor(repository);
    }

    @Provides
    @Singleton
    public MainInteractor provideMainInteractor(DataBaseRepository dataBaseRepository, ProfileRepository profileRepository) {
        return new MainInteractor(dataBaseRepository, profileRepository);
    }

    @Provides
    @Singleton
    public PrimaryInteractor providePrimaryInteractor(AdvertisingRepository repository, SportsNutritionRepository sportsNutritionRepository) {
        return new PrimaryInteractor(repository, sportsNutritionRepository);
    }

    @Provides
    @Singleton
    public CategoryInteractor provideCategoryInteractor(SportsNutritionRepository repository) {
        return new CategoryInteractor(repository);
    }

    @Provides
    @Singleton
    public ProductsCategoryInteractor provideProductsCategoryInteractor(SportsNutritionRepository serviceRepository,
                                                                        DataBaseRepository dataBaseRepository) {
        return new ProductsCategoryInteractor(serviceRepository, dataBaseRepository);
    }

    @Provides
    @Singleton
    public AboutAppInteractor provideAboutAppInteractor(AdvertisingRepository repository) {
        return new AboutAppInteractor(repository);
    }

    @Provides
    @Singleton
    public FavoriteInteractor provideFavoriteInteractor(DataBaseRepository repository) {
        return new FavoriteInteractor(repository);
    }

    @Provides
    @Singleton
    public BasketInteractor provideBasketInteractor(DataBaseRepository repository) {
        return new BasketInteractor(repository);
    }

    @Provides
    @Singleton
    public ProductDescriptionInteractor provideProductDescriptionInteractor(DataBaseRepository repository) {
        return new ProductDescriptionInteractor(repository);
    }

    @Provides
    @Singleton
    public SelfDeliveryMethodInteractor provideSelfDeliveryMethodInteractor(ProfileRepository repository) {
        return new SelfDeliveryMethodInteractor(repository);
    }

    @Provides
    @Singleton
    public MethodCourierInteractor provideMethodCourierInteractor(ProfileRepository repository) {
        return new MethodCourierInteractor(repository);
    }

    @Provides
    @Singleton
    public SearchInteractor provideSearchInteractor(DataBaseRepository repository) {
        return new SearchInteractor(repository);
    }

    @Provides
    @Singleton
    public SettingsInteractor provideSettingsInteractor(DataBaseRepository repository) {
        return new SettingsInteractor(repository);
    }

    @Provides
    @Singleton
    public MapInteractor provideMapInteractor(SportsNutritionRepository repository, MapRepository mapRepository) {
        return new MapInteractor(repository, mapRepository);
    }

    @Provides
    @Singleton
    public BrandInteractor provideBrandInteractor(DataBaseRepository repository) {
        return new BrandInteractor(repository);
    }
}
