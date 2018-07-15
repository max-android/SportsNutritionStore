package ru.exampleopit111.sportsnutritionstore.presentation.presenter.search;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.search.SearchInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.search.SearchView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Filter;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 06.07.2018.
 * Copyright © Max
 */
public class SearchPresenter extends BasePresenter<SearchView> {

    @Inject
    SearchInteractor interactor;
    @Inject
    Router router;
    @Inject
    ResourceManager resourceManager;

    private String filter;
    private List<Product> productsList;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SearchPresenter(SearchInteractor interactor, Router router, ResourceManager resourceManager) {
        this.interactor = interactor;
        this.router = router;
        this.resourceManager = resourceManager;
        productsList = new ArrayList<>();
    }

    public void attach(String search){
        disposable.add(
                interactor.search(search)
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(response -> {
                                    updateProductsList(response);
                                    view.showProducts(response);
                                }, error -> {
                                    view.showMessage(resourceManager.getString(R.string.error_search));
                                }
                        ));
    }

    private void updateProductsList(List<Product> response){

        if (productsList.isEmpty()) {
            productsList.addAll(response);
        }else {
            productsList.clear();
            if(!filter.isEmpty()){
                productsList.addAll(Filter.filter(filter,response));
            }else{
                productsList.addAll(response);
            }
        }
    }


    public void doFilter(String filter){
        if(!filter.isEmpty()){
            this.filter = filter;
            view.showProducts(Filter.filter(filter,productsList));
        }
    }

    public void doListRecycler(){
        view.showProducts(productsList);
    }

    public void doGridRecycler(){
        view.showProducts(productsList);
    }

    public void onProductClicked(Product product) {
        router.replaceScreen(Screens.PRODUCT_DESCRIPTION_FRAGMENT,product);
    }

    @SuppressLint("CheckResult")
    public void onFavotiteClicked(Product product,String search) {
        Completable.fromCallable(
                () -> {
                    interactor.updateFavorite(product.getName(),updateStatus(product.isFavorite()));
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> attach(search),
                (error) -> view.showError(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void onBasketClicked(Product product,String search) {
        Completable.fromCallable(
                () -> {
                    interactor.updateBasket(product.getName(),updateStatus(product.isBasket()));
                    return null;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                                attach(search);
                            },
                        (error) -> view.showError(error.getMessage()));
    }

    private boolean updateStatus(boolean status){
        if(status){
            status = false;
        }else{
            status = true;
        }
        return status;
    }

    public void closeResources() {
        disposable.clear();
    }

}
