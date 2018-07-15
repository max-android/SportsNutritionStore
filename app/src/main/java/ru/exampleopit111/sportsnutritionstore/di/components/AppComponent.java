package ru.exampleopit111.sportsnutritionstore.di.components;


import javax.inject.Singleton;

import dagger.Component;
import ru.exampleopit111.sportsnutritionstore.di.modules.CiceroneModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.DatabaseModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.FileModule;
import ru.exampleopit111.sportsnutritionstore.di.modules.FirebaseModule;
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
import ru.exampleopit111.sportsnutritionstore.ui.about_app.AboutAppFragment;
import ru.exampleopit111.sportsnutritionstore.ui.base.DrawerActivity;
import ru.exampleopit111.sportsnutritionstore.ui.base.NavigateActivity;
import ru.exampleopit111.sportsnutritionstore.ui.base.ToolbarActivity;
import ru.exampleopit111.sportsnutritionstore.ui.basket.BasketFragment;
import ru.exampleopit111.sportsnutritionstore.ui.brand.BrandFragment;
import ru.exampleopit111.sportsnutritionstore.ui.category.CategoryFragment;
import ru.exampleopit111.sportsnutritionstore.ui.favorite.FavoriteFragment;
import ru.exampleopit111.sportsnutritionstore.ui.main.MainActivity;
import ru.exampleopit111.sportsnutritionstore.ui.main.MainFragment;
import ru.exampleopit111.sportsnutritionstore.ui.map.MapFragment;
import ru.exampleopit111.sportsnutritionstore.ui.method_courier.MethodCourierFragment;
import ru.exampleopit111.sportsnutritionstore.ui.method_receiving_order.MethodReceivingOrderFragment;
import ru.exampleopit111.sportsnutritionstore.ui.primary.PrimaryFragment;
import ru.exampleopit111.sportsnutritionstore.ui.products_category.ProductsCategoryFragment;
import ru.exampleopit111.sportsnutritionstore.ui.registration.LoginActivity;
import ru.exampleopit111.sportsnutritionstore.ui.registration.MailRegistrationFragment;
import ru.exampleopit111.sportsnutritionstore.ui.registration.PhoneRegistrationFragment;
import ru.exampleopit111.sportsnutritionstore.ui.search.SearchFragment;
import ru.exampleopit111.sportsnutritionstore.ui.self_delivery_method.SelfDeliveryMethodFragment;
import ru.exampleopit111.sportsnutritionstore.ui.settings.SettingsFragment;
import ru.exampleopit111.sportsnutritionstore.ui.sports_nutrition_product.ProductDescriptionFragment;
import ru.exampleopit111.sportsnutritionstore.ui.start.StartActivity;


@Singleton
@Component(modules = {
        GlideModule.class,
        DatabaseModule.class,
        NetInspectorModule.class,
        ResourceModule.class,
        CiceroneModule.class,
        UserDataHolderModule.class,
        FirebaseModule.class,
        FileModule.class,
        ServiceModule.class,
        NetworkModule.class,
        PermissionModule.class,
        ThemeInstanceModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        PresenterModule.class
})

public interface AppComponent {
    void injectWelcomeActivity(StartActivity welcomeActivity);

    void injectMainActivity(MainActivity mainActivity);

    void injectMainFragment(MainFragment mainFragment);

    void injectPrimaryFragment(PrimaryFragment primaryFragment);

    void injectCategoryFragment(CategoryFragment categoryFragment);

    void injectFavoriteFragment(FavoriteFragment favoriteFragment);

    void injectBasketFragment(BasketFragment basketFragment);

    void injectDrawerActivity(DrawerActivity mainActivity);

    void injectToolbarActivity(ToolbarActivity toolBarActivity);

    void injectNavigateActivity(NavigateActivity navigateActivity);

    void injectSettingsFragment(SettingsFragment settingsFragment);

    void injectMapFragmentFragment(MapFragment mapFragment);

    void injectAboutAppFragment(AboutAppFragment aboutAppFragment);

    void injectPhoneRegistrationFragment(PhoneRegistrationFragment phoneRegistrationFragment);

    void injectMailRegistrationFragment(MailRegistrationFragment mailRegistrationFragment);

    void injectLoginActivity(LoginActivity loginActivity);

    void injectProductsCategoryFragment(ProductsCategoryFragment productsCategoryFragment);

    void injectProductDescriptionFragment(ProductDescriptionFragment productDescriptionFragment);

    void injectSearchFragment(SearchFragment searchFragment);

    void injectMethodReceivingOrderFragment(MethodReceivingOrderFragment receivingOrderFragment);

    void injectMethodCourierFragment(MethodCourierFragment methodCourierFragment);

    void injectSelfDeliveryMethodFragment(SelfDeliveryMethodFragment selfDeliveryMethodFragment);

    void injectBrandFragment(BrandFragment brandFragment);
}
