package ru.exampleopit111.sportsnutritionstore.presentation.presenter.primary;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.primary.PrimaryInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.primary.PrimaryView;
import ru.exampleopit111.sportsnutritionstore.ui.common.NetInspector;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class PrimaryPresenter extends BasePresenter<PrimaryView> {

    @Inject
    PrimaryInteractor interactor;
    @Inject
    NetInspector inspector;
    @Inject
    ResourceManager resourceManager;
    @Inject
    Router router;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public PrimaryPresenter(PrimaryInteractor interactor,
                            NetInspector inspector,
                            ResourceManager resourceManager,
                            Router router
    ) {
        this.interactor = interactor;
        this.inspector = inspector;
        this.resourceManager = resourceManager;
        this.router = router;
    }

    public void attach() {
        showGallery();
        showBrands();
    }

    private void showGallery() {
        if (inspector.isOnline()) {
            disposable.add(
                    interactor.getData()
                            .doOnSubscribe(disposable -> view.showProgress())
                            .doFinally(() -> view.removeProgress())
                            .subscribe(response -> {
                                        view.showGallery(response);
                                    }, error -> {
                                        view.showError(error.getMessage());
                                    }
                            ));
        } else {
            view.showMessage(resourceManager.getString(R.string.not_network));
        }

    }

    private void showBrands() {
        disposable.add(
                interactor.getBrands()
                        .subscribe(response -> {
                                    view.showBrands(response);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }

    public void onBrandClick(Brand brand) {
        router.replaceScreen(Screens.BRAND_FRAGMENT, brand);
    }

    public void closeResources() {
        disposable.clear();
    }
}
