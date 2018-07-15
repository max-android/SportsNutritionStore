package ru.exampleopit111.sportsnutritionstore.presentation.presenter.favorite;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.domain.favorite.FavoriteInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.favorite.FavoriteView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class FavoritePresenter extends BasePresenter<FavoriteView> {

    @Inject
    FavoriteInteractor interactor;
    @Inject
    Router router;
    @Inject
    ResourceManager resourceManager;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public FavoritePresenter(FavoriteInteractor interactor, Router router, ResourceManager resourceManager) {
        this.interactor = interactor;
        this.router = router;
        this.resourceManager = resourceManager;
    }

    public void attach() {
        disposable.add(
                interactor.favorites()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(favorites -> {
                                    view.showProducts(favorites);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));

    }

    public void onProductClicked(Product product) {
        router.replaceScreen(Screens.PRODUCT_DESCRIPTION_FRAGMENT, product);
    }

    @SuppressLint("CheckResult")
    public void onFavotiteClicked(Product product) {
        Completable.fromCallable(
                () -> {
                    interactor.updateFavorite(product.getName(), updateStatus(product.isFavorite()));
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(),
                        (error) -> view.showError(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void onBasketClicked(Product product) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(), updateStatus(product.isBasket()));
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(),
                        (error) -> view.showError(error.getMessage()));
    }

    private boolean updateStatus(boolean status) {
        if (status) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void closeResources() {
        disposable.clear();
    }
}
