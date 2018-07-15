package ru.exampleopit111.sportsnutritionstore.ui.about_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.about_app.AboutAppPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.about_app.AboutAppView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;

/**
 * Created Максим on 10.06.2018.
 * Copyright © Max
 */
public class AboutAppFragment extends BaseFragment implements AboutAppView {

    @BindView(R.id.aboutAppTextView)
    TextView aboutAppTextView;
    @BindView(R.id.aboutAppProgressBar)
    ProgressBar aboutAppProgressBar;

    @Inject
    AboutAppPresenter presenter;

    private Notification notification;

    public AboutAppFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_about_app;
    }

    public static AboutAppFragment newInstance() {
        AboutAppFragment fragment = new AboutAppFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectAboutAppFragment(this);
        presenter.bindView(this);
        notification = new Notification(view, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.attach();
    }

    @Override
    public void showProgress() {
        aboutAppProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        aboutAppProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAboutApp(String response) {
        aboutAppTextView.setText(response);
    }

    @Override
    public void showError(String message) {
        notification.showMessage(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}
