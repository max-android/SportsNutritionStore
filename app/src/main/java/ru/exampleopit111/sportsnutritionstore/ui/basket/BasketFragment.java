package ru.exampleopit111.sportsnutritionstore.ui.basket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.basket.BasketPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.basket.BasketView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.BasketAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;
import ru.exampleopit111.sportsnutritionstore.ui.custom.CustomDialog;
import ru.exampleopit111.sportsnutritionstore.ui.registration.LoginActivity;

/**
 * Created Максим on 02.07.2018.
 * Copyright © Max
 */
public class BasketFragment extends BaseFragment implements BasketView {
    @BindView(R.id.entireBasketLinearLayout)
    LinearLayout entireBasketLinearLayout;
    @BindView(R.id.emptyBasketLinearLayout)
    LinearLayout emptyBasketLinearLayout;
    @BindView(R.id.basketSwipeRefreshLayout)
    SwipeRefreshLayout basketSwipeRefresh;
    @BindView(R.id.basketRecyclerView)
    RecyclerView basketRecyclerView;
    @BindView(R.id.basketProgressBar)
    ProgressBar basketProgressBar;
    @BindView(R.id.totalCoastTextView)
    TextView totalCoastTextView;
    @BindView(R.id.checkoutButton)
    Button checkoutButton;

    @Inject
    ResourceManager resourceManager;
    @Inject
    RequestManager requestManager;
    @Inject
    BasketPresenter presenter;

    private Notification notification;
    private List<Product> basketList;
    private BasketAdapter adapter;
    private boolean update_click;

    public BasketFragment() {
    }

    public static BasketFragment newInstance() {
        BasketFragment fragment = new BasketFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_basket;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectBasketFragment(this);
        notification = new Notification(view, getContext());
        basketList = new ArrayList<>();
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach();
    }

    private void initComponents() {
        basketSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        SwipeManager swipeManager = new SwipeManager(basketSwipeRefresh, basketProgressBar,
                () -> presenter.attach());
        basketSwipeRefresh.setOnRefreshListener(swipeManager);
        basketSwipeRefresh.setRefreshing(false);
        basketRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkoutButton.setOnClickListener(v -> {
                    presenter.onClickCheckoutButton(
                            new BasketEntity(basketList, totalCoastTextView.getText().toString()));
                }
        );
    }

    @Override
    public void showProducts(List<Product> products) {
        checkExistBasketProducts(products);
        updateOrderPrice(products);
        if (update_click) {
            update_click = false;
            adapter.updateData(products);
        } else {
            if (adapter != null) {
                clearRecyclerView();
            }
            basketList.addAll(products);
            initAdapter();
        }
    }

    private void checkExistBasketProducts(List<Product> products) {
        if (products.isEmpty()) {
            entireBasketLinearLayout.setVisibility(View.GONE);
            emptyBasketLinearLayout.setVisibility(View.VISIBLE);
        } else {
            emptyBasketLinearLayout.setVisibility(View.GONE);
            entireBasketLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        adapter = new BasketAdapter(basketList,
                product ->
                        presenter.onProductClicked(product),
                requestManager,
                product -> {
                    presenter.onItemDeleteClicked(product);
                    update_click = true;
                    notifAboutUpdateBasket();
                },
                resourceManager,
                (product, value) -> {
                    presenter.onPlusClicked(product, value);
                    update_click = true;
                },
                (product, value) -> {
                    presenter.onMinusClicked(product, value);
                    update_click = true;
                }
        );
        basketRecyclerView.setAdapter(adapter);
    }

    private void clearRecyclerView() {
        if (!basketList.isEmpty()) {
            basketList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void notifAboutUpdateBasket() {
        notification.showMessage(resourceManager.getString(R.string.delete_product_on_basket));
    }

    private void updateOrderPrice(List<Product> products) {
        int count = 0;
        for (Product product : products) {
            count = count + product.getPrice() * product.getCurrentOrder();
        }
        totalCoastTextView.setText(String.format("%d%s", count,
                resourceManager.getString(R.string.unit_of_price)));
    }

    private void showDialog() {
        new CustomDialog.DialogBuilder()
                .from(getContext())
                .title(resourceManager.getString(R.string.must_register))
                .message(resourceManager.getString(R.string.registration_capabilities))
                .negativeButton(resourceManager.getString(R.string.abolish), () -> {
                    notification.showMessageWithAction(resourceManager.getString(R.string.purchase_requires_registration),
                            () -> {
                                goToRegistration();
                            });

                })
                .positiveButton(resourceManager.getString(R.string.ok_action), () -> {
                    goToRegistration();
                }, true)
                .create()
                .show(getChildFragmentManager(), Constants.DIALOG_KEY);
    }

    private void goToRegistration() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void showMessage(String message) {
        notification.showMessage(message);
    }

    @Override
    public void showProgress() {
        basketProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        basketProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void noRegistration() {
        showDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
    }
}
