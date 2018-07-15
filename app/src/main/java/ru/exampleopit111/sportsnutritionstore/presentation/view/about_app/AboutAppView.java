package ru.exampleopit111.sportsnutritionstore.presentation.view.about_app;

/**
 * Created Максим on 24.06.2018.
 * Copyright © Max
 */
public interface AboutAppView {
    void showProgress();

    void removeProgress();

    void showAboutApp(String response);

    void showError(String message);

}
