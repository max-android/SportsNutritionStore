package ru.exampleopit111.sportsnutritionstore.di.modules;

import com.google.firebase.auth.FirebaseAuth;

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
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.about_app.AboutAppPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.basket.BasketPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.brand.BrandPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.category.CategoryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.favorite.FavoritePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.main.MainPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.map.MapPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_courier.MethodCourierPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_receiving_order.MethodReceivingOrderPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.primary.PrimaryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.product_description.ProductDescriptionPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.products_category.ProductsCategoryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.LoginActivityPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.MailRegistrationPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.PhoneRegistrationPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.search.SearchPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.self_delivery_method.SelfDeliveryMethodPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.settings.SettingsPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.start.StartPresenter;
import ru.exampleopit111.sportsnutritionstore.ui.common.NetInspector;
import ru.terrakok.cicerone.Router;

/**
 * Created by Максим on 07.06.2018.
 */


@Module
@Singleton
public class PresenterModule {

    @Provides
    @Singleton
    public StartPresenter provideStartPresenter() {
        return new StartPresenter();
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(ResourceManager resourceManager, Router router,
                                              MainInteractor interactor, FirebaseAuth firebaseAuth) {
        return new MainPresenter(resourceManager, router, interactor, firebaseAuth);
    }

    @Provides
    @Singleton
    public PhoneRegistrationPresenter providePhoneRegPresenter(PhoneRegistrationInteractor interactor,
                                                               Router router) {
        return new PhoneRegistrationPresenter(interactor, router);
    }

    @Provides
    @Singleton
    public LoginActivityPresenter provideLoginActivityPresenter(PhoneRegistrationInteractor interactor,
                                                                Router router) {
        return new LoginActivityPresenter(interactor, router);
    }

    @Provides
    @Singleton
    public MailRegistrationPresenter provideMailRegistrationPresenter(MailRegistrationInteractor interactor) {
        return new MailRegistrationPresenter(interactor);
    }

    @Provides
    @Singleton
    public PrimaryPresenter providePrimaryPresenter(PrimaryInteractor interactor, NetInspector inspector, ResourceManager resourceManager, Router router) {
        return new PrimaryPresenter(interactor, inspector, resourceManager, router);
    }

    @Provides
    @Singleton
    public CategoryPresenter provideCategoryPresenter(CategoryInteractor interactor, Router router) {
        return new CategoryPresenter(interactor, router);
    }

    @Provides
    @Singleton
    public ProductsCategoryPresenter provideProductsCategoryPresenter(ProductsCategoryInteractor interactor,
                                                                      Router router, ResourceManager resourceManager) {
        return new ProductsCategoryPresenter(interactor, router, resourceManager);
    }

    @Provides
    @Singleton
    public AboutAppPresenter provideAboutAppPresenter(AboutAppInteractor interactor) {
        return new AboutAppPresenter(interactor);
    }

    @Provides
    @Singleton
    public FavoritePresenter provideFavoritePresenter(FavoriteInteractor interactor, Router router, ResourceManager resourceManager) {
        return new FavoritePresenter(interactor, router, resourceManager);
    }

    @Provides
    @Singleton
    public BasketPresenter provideBasketPresenter(BasketInteractor interactor, Router router, ResourceManager resourceManager, FirebaseAuth firebaseAuth) {
        return new BasketPresenter(interactor, router, resourceManager, firebaseAuth);
    }

    @Provides
    @Singleton
    public ProductDescriptionPresenter provideProductDescriptionPresenter(ProductDescriptionInteractor interactor, ResourceManager resourceManager) {
        return new ProductDescriptionPresenter(interactor, resourceManager);
    }

    @Provides
    @Singleton
    public MethodReceivingOrderPresenter provideMethodReceivingOrderPresenter(Router router) {
        return new MethodReceivingOrderPresenter(router);
    }

    @Provides
    @Singleton
    public SelfDeliveryMethodPresenter provideSelfDeliveryMethodPresenter(SelfDeliveryMethodInteractor interactor, ResourceManager resourceManager, Router router) {
        return new SelfDeliveryMethodPresenter(interactor, resourceManager, router);
    }

    @Provides
    @Singleton
    public MethodCourierPresenter provideMethodCourierPresenter(MethodCourierInteractor interactor, ResourceManager resourceManager, Router router) {
        return new MethodCourierPresenter(interactor, resourceManager, router);
    }

    @Provides
    @Singleton
    public SearchPresenter provideSearchPresenter(SearchInteractor interactor, Router router, ResourceManager resourceManager) {
        return new SearchPresenter(interactor, router, resourceManager);
    }

    @Provides
    @Singleton
    public SettingsPresenter provideSettingsPresenter(SettingsInteractor interactor, ResourceManager resourceManager) {
        return new SettingsPresenter(interactor, resourceManager);
    }

    @Provides
    @Singleton
    public MapPresenter provideMapPresenter(MapInteractor interactor) {
        return new MapPresenter(interactor);
    }

    @Provides
    @Singleton
    public BrandPresenter provideBrandPresenter(BrandInteractor interactor, Router router, ResourceManager resourceManager) {
        return new BrandPresenter(interactor, router, resourceManager);
    }
}
