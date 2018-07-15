package ru.exampleopit111.sportsnutritionstore.ui.primary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.database.AppBase;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.primary.PrimaryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.primary.PrimaryView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.BrandsAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.MyWebViewClient;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;

/**
 * Created Максим on 07.06.2018.
 * Copyright © Max
 */
public class PrimaryFragment extends BaseFragment implements PrimaryView {

    @BindView(R.id.primaryWebView)
    WebView webView;
    @BindView(R.id.primaryProgressBar)
    ProgressBar primaryProgressBar;
    @BindView(R.id.primarySwipeRefresh)
    SwipeRefreshLayout primarySwipeRefresh;
    @BindView(R.id.brandRecyclerView)
    RecyclerView brandRecyclerView;

    @Inject
    PrimaryPresenter presenter;
    @Inject
    RequestManager requestManager;
    @Inject
    AppBase base;

    private Notification notification;
    private SwipeManager swipeManager;

    public PrimaryFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_primary;
    }

    public static PrimaryFragment newInstance() {
        PrimaryFragment fragment = new PrimaryFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectPrimaryFragment(this);
        notification = new Notification(view, getContext());
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach();
    }

    private void initComponents() {
        webView.getSettings().setJavaScriptEnabled(true);
        primarySwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeManager = new SwipeManager(primarySwipeRefresh, primaryProgressBar, () -> presenter.attach());
        primarySwipeRefresh.setOnRefreshListener(swipeManager);
        primarySwipeRefresh.setRefreshing(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        brandRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showGallery(String url) {
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void showMessage(String message) {
        notification.showMessage(message);
    }

    @Override
    public void showProgress() {
        primaryProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        primaryProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showBrands(List<Brand> brands) {
        brandRecyclerView.setAdapter(new BrandsAdapter(brands,
                brand -> presenter.onBrandClick(brand),
                requestManager));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}
