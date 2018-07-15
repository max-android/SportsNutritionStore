package ru.exampleopit111.sportsnutritionstore.presentation.presenter.base;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public class BasePresenter<T> {
    protected T view;

    public void bindView(T view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }

}
