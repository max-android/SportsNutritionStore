package ru.exampleopit111.sportsnutritionstore.presentation.presenter.about_app;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.exampleopit111.sportsnutritionstore.domain.about_app.AboutAppInteractor;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.about_app.AboutAppView;

/**
 * Created Максим on 24.06.2018.
 * Copyright © Max
 */
public class AboutAppPresenter extends BasePresenter<AboutAppView> {

    @Inject
    AboutAppInteractor interactor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public AboutAppPresenter(AboutAppInteractor interactor) {
        this.interactor = interactor;
    }

    public void attach() {
        disposable.add(
                interactor.getData()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .doFinally(() -> view.removeProgress())
                        .subscribe(response -> {
                                    view.showAboutApp(response);
                                }, error -> {
                                    view.showError(error.getMessage());
                                }
                        ));
    }

    public void closeResources() {
        disposable.clear();
    }
}
