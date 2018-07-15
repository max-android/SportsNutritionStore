package ru.exampleopit111.sportsnutritionstore.presentation.presenter.basket;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.domain.basket.BasketInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.basket.BasketView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class BasketPresenter extends BasePresenter<BasketView> {

    @Inject
    BasketInteractor interactor;
    @Inject
    Router router;
    @Inject
    ResourceManager resourceManager;
    @Inject
    FirebaseAuth firebaseAuth;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public BasketPresenter(BasketInteractor interactor, Router router, ResourceManager resourceManager, FirebaseAuth firebaseAuth) {
        this.interactor = interactor;
        this.router = router;
        this.resourceManager = resourceManager;
        this.firebaseAuth = firebaseAuth;
    }

    public void attach() {
        disposable.add(
                interactor.basket()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(products -> {
                                    view.showProducts(products);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }

    public void onProductClicked(Product product) {
        router.replaceScreen(Screens.PRODUCT_DESCRIPTION_FRAGMENT, product);
    }

    @SuppressLint("CheckResult")
    public void onItemDeleteClicked(Product product) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(), Constants.DEFAULT_COUNT_PRODUCT, false);
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(),
                        (error) -> view.showError(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void onPlusClicked(Product product, int count) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(), count, true);
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(),
                        (error) -> view.showError(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void onMinusClicked(Product product, int count) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(), count, true);
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(),
                        (error) -> view.showError(error.getMessage()));
    }

    public void onClickCheckoutButton(BasketEntity basketEntity) {
        if (firebaseAuth.getCurrentUser() != null) {
            router.replaceScreen(Screens.METHOD_RECEIVING_ORDER_FRAGMENT, basketEntity);
        } else {
            view.noRegistration();
        }
    }

    public void closeResources() {
        disposable.clear();
    }

}
