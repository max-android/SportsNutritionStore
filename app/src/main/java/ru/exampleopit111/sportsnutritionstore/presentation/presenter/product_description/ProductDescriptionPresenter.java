package ru.exampleopit111.sportsnutritionstore.presentation.presenter.product_description;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.product_description.ProductDescriptionInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.product_description.ProductDescriptionView;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class ProductDescriptionPresenter extends BasePresenter<ProductDescriptionView> {

    @Inject
    ProductDescriptionInteractor interactor;
    @Inject
    ResourceManager resourceManager;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public ProductDescriptionPresenter(ProductDescriptionInteractor interactor, ResourceManager resourceManager) {
        this.interactor = interactor;
        this.resourceManager = resourceManager;
    }

    @SuppressLint("CheckResult")
    public void onBusketButtonClick(Product product) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(), true);
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showMessage(resourceManager.getString(R.string.add_product_on_basket)),
                        (error) -> view.showError(error.getMessage()));
    }

    public void closeResources() {
        disposable.clear();
    }
}
