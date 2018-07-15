package ru.exampleopit111.sportsnutritionstore.presentation.presenter.category;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.exampleopit111.sportsnutritionstore.domain.category.CategoryInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.category.CategoryView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;


/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class CategoryPresenter extends BasePresenter<CategoryView> {

    @Inject
    CategoryInteractor interactor;
    @Inject
    Router router;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public CategoryPresenter(CategoryInteractor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    public void attach() {
        initCategories();
    }

    private void initCategories() {
        disposable.add(
                interactor.categories()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(response -> {
                                    view.showCategories(response);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }


    public void closeResources() {
        disposable.clear();
    }

    public void onCategoryClicked(ProductCategory category) {
        router.replaceScreen(Screens.PRODUCTS_CATEGORY_FRAGMENT, category);
    }
}
