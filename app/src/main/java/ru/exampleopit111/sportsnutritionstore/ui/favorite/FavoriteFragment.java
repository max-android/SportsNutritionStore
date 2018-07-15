package ru.exampleopit111.sportsnutritionstore.ui.favorite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.favorite.FavoritePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.favorite.FavoriteView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.ProductsAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.AdapterViewType;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class FavoriteFragment extends BaseFragment implements FavoriteView {

    @BindView(R.id.entireFavoriteRelativeLayout)
    RelativeLayout entireFavoriteRelativeLayout;
    @BindView(R.id.emptyFavoriteLinearLayout)
    LinearLayout emptyFavoriteLinearLayout;
    @BindView(R.id.favoriteSwipeRefreshLayout)
    SwipeRefreshLayout favoriteSwipeRefresh;
    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;
    @BindView(R.id.favoriteProgressBar)
    ProgressBar favoriteProgressBar;


    @Inject
    ResourceManager resourceManager;
    @Inject
    RequestManager requestManager;
    @Inject
    FavoritePresenter presenter;

    private Notification notification;
    private List<Product> favoriteList;
    private ProductsAdapter adapter;
    private boolean image_click;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectFavoriteFragment(this);
        notification = new Notification(view, getContext());
        favoriteList = new ArrayList<>();
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach();
    }

    private void initComponents() {
        favoriteSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        SwipeManager swipeManager = new SwipeManager(favoriteSwipeRefresh, favoriteProgressBar,
                () -> presenter.attach());
        favoriteSwipeRefresh.setOnRefreshListener(swipeManager);
        favoriteSwipeRefresh.setRefreshing(false);
        favoriteRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showProducts(List<Product> products) {
        checkExistFavoritProducts(products);
        if (image_click) {
            image_click = false;
            adapter.updateData(products);
        } else {
            clearRecyclerView();
            favoriteList.addAll(products);
            initAdapter();
        }
    }

    private void checkExistFavoritProducts(List<Product> products) {
        if (products.isEmpty()) {
            entireFavoriteRelativeLayout.setVisibility(View.GONE);
            emptyFavoriteLinearLayout.setVisibility(View.VISIBLE);
        } else {
            emptyFavoriteLinearLayout.setVisibility(View.GONE);
            entireFavoriteRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        adapter = new ProductsAdapter(favoriteList,
                resourceManager,
                product ->
                        presenter.onProductClicked(product),
                requestManager,
                product -> {
                    presenter.onBasketClicked(product);
                    image_click = true;
                    notifAboutUpdateBasket(product.isBasket());
                },
                product -> {
                    presenter.onFavotiteClicked(product);
                    image_click = true;
                    notifAboutUpdateFavorite(product.isFavorite());
                },
                AdapterViewType.LIN_TYPE
        );
        favoriteRecyclerView.setAdapter(adapter);
    }

    private void clearRecyclerView() {
        if (!favoriteList.isEmpty()) {
            favoriteList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void notifAboutUpdateFavorite(boolean favorite) {
        if (favorite) {
            notification.showMessage(resourceManager.getString(R.string.delete_product_on_favorite));
        } else {
            notification.showMessage(resourceManager.getString(R.string.add_product_on_favorite));
        }
    }

    private void notifAboutUpdateBasket(boolean basket) {
        if (basket) {
            notification.showMessage(resourceManager.getString(R.string.delete_product_on_basket));
        } else {
            notification.showMessage(resourceManager.getString(R.string.add_product_on_basket));
        }
    }

    @Override
    public void showProgress() {
        favoriteProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        favoriteProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
        entireFavoriteRelativeLayout.setVisibility(View.GONE);
        emptyFavoriteLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}
