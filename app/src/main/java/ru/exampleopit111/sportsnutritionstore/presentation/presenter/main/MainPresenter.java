package ru.exampleopit111.sportsnutritionstore.presentation.presenter.main;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.main.MainInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.PositionTabObj;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.SearchEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.main.MainView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 07.06.2018.
 * Copyright © Max
 */
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    ResourceManager resourceManager;
    @Inject
    Router router;
    @Inject
    MainInteractor interactor;
    @Inject
    FirebaseAuth firebaseAuth;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private Handler handler;

    public MainPresenter(ResourceManager resourceManager,
                         Router router,
                         MainInteractor interactor,
                         FirebaseAuth firebaseAuth) {
        this.resourceManager = resourceManager;
        this.router = router;
        this.interactor = interactor;
        this.firebaseAuth = firebaseAuth;
        handler = new Handler(Looper.getMainLooper());
    }

    public void initProfile() {

        Executors.newSingleThreadExecutor().execute(() -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            String mail = "";
            if (currentUser != null) {
                mail = currentUser.getEmail();
            }

            String phone_number = interactor.user().getPreferences()
                    .getString(Constants.KEY_PHONE_HOLDER, Constants.DEFAULT_PHONE);

            String url = "";

            if (interactor.profile().checkExistFile()) {
                url = interactor.profile().readFile();
            } else {
                url = resourceManager.getString(R.string.default_profile);
            }
            String finalMail = mail;
            String finalUrl = url;
            handler.post(() -> {
                view.setProfile(finalMail, phone_number, finalUrl);
            });

        });
    }

    public void initCountOrdersBasket() {
        disposable.add(
                interactor.basket()
                        .subscribe(products -> {
                                    view.setCountOrdersBusket(products);
                                }, error -> {
                                    Log.d(Constants.LOG, error.getMessage());
                                }
                        ));
    }

    public void initCountFavoriteProducts() {
        disposable.add(
                interactor.favorite()
                        .subscribe(products -> {
                                    view.setCountFavoriteProducts(products);
                                }, error -> {
                                    Log.d(Constants.LOG, error.getMessage());
                                }
                        ));
    }

    public void onClickMain(int position) {
        router.replaceScreen(Screens.MAIN_FRAGMENT, new PositionTabObj(position));
    }

    public void onClickBasket() {
        router.replaceScreen(Screens.BASKET_FRAGMENT);
    }

    public void onClickLocationStores() {
        router.replaceScreen(Screens.MAP_FRAGMENT);
    }

    public void onClickSettings() {
        router.replaceScreen(Screens.SETTINGS_FRAGMENT);
    }

    public void onClickAboutApp() {
        router.replaceScreen(Screens.ABOUT_APP_FRAGMENT);
    }

    public void onClickCategory(ProductCategory category) {
        router.replaceScreen(Screens.PRODUCTS_CATEGORY_FRAGMENT, category);
    }

    public void onClickSearch(SearchEntity searchEntity) {
        router.replaceScreen(Screens.SEARCH_FRAGMENT, searchEntity);
    }

    public void onClickBrand(Brand brand) {
        router.replaceScreen(Screens.BRAND_FRAGMENT, brand);
    }

    public void onClickReceivingOrder(BasketEntity basketEntity) {
        router.replaceScreen(Screens.METHOD_RECEIVING_ORDER_FRAGMENT, basketEntity);
    }

    public void closeResources() {
        disposable.clear();
    }
}
