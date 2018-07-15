package ru.exampleopit111.sportsnutritionstore.presentation.presenter.map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.exampleopit111.sportsnutritionstore.domain.map.MapInteractor;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.map.MapView;

/**
 * Created Максим on 09.07.2018.
 * Copyright © Max
 */
public class MapPresenter extends BasePresenter<MapView> {

    @Inject
    MapInteractor interactor;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public MapPresenter(MapInteractor interactor) {
        this.interactor = interactor;
    }

    public void attach() {
        disposable.add(
                interactor.locationStores()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(response -> {
                                    view.showStores(response);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }

    public void initRouter(String position, String destination,
                           boolean sensor, String language, String key) {
        disposable.add(
                interactor.route(position, destination, sensor, language, key)
                        .subscribe(response -> {
                                    view.showRoute(response);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }

    public void closeResources() {
        disposable.clear();
    }
}
