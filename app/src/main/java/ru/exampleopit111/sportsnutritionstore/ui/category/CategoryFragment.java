package ru.exampleopit111.sportsnutritionstore.ui.category;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.category.CategoryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.category.CategoryView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.CategoryAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class CategoryFragment extends BaseFragment implements CategoryView {
    @BindView(R.id.categoryRecyclerView)
    RecyclerView categoryRecyclerView;
    @BindView(R.id.categoryProgressBar)
    ProgressBar categoryProgressBar;
    @BindView(R.id.categorySwipeRefresh)
    SwipeRefreshLayout categorySwipeRefresh;

    @Inject
    CategoryPresenter presenter;

    private Notification notification;
    private SwipeManager swipeManager;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectCategoryFragment(this);
        notification = new Notification(view, getContext());
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        categorySwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeManager = new SwipeManager(categorySwipeRefresh, categoryProgressBar,
                () -> presenter.attach());
        categorySwipeRefresh.setOnRefreshListener(swipeManager);
        categorySwipeRefresh.setRefreshing(false);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        presenter.attach();
    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        categoryRecyclerView.setAdapter(new CategoryAdapter(categories,
                category -> presenter.onCategoryClicked(category)));
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void showProgress() {
        categoryProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        categoryProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}
