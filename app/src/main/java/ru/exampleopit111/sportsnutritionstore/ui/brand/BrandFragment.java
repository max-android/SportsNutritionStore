package ru.exampleopit111.sportsnutritionstore.ui.brand;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.brand.BrandPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.brand.BrandView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.AdapterViewType;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.ProductsAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;


/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public class BrandFragment extends BaseFragment implements BrandView {

    @BindView(R.id.productsBrandRecyclerView)
    RecyclerView productsBrandRecyclerView;
    @BindView(R.id.productsBrandProgressBar)
    ProgressBar productsBrandProgressBar;
    @BindView(R.id.productsBrandSwipeRefresh)
    SwipeRefreshLayout productsBrandSwipeRefresh;
    @BindView(R.id.productsBrandSpinner)
    Spinner productsBrandSpinner;
    @BindView(R.id.gridImageButton)
    ImageButton gridImageButton;
    @BindView(R.id.linImageButton)
    ImageButton linImageButton;

    @Inject
    ResourceManager resourceManager;
    @Inject
    BrandPresenter presenter;
    @Inject
    RequestManager requestManager;

    private AdapterViewType viewType;
    private String[] filters;
    private Notification notification;
    private SwipeManager swipeManager;
    private List<Product> productsBrandList;
    private ProductsAdapter adapter;
    private boolean image_click;

    public BrandFragment() {
    }

    public static BrandFragment newInstance(Brand brand) {
        BrandFragment fragment = new BrandFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BRAND_KEY, brand);
        fragment.setArguments(args);
        return fragment;
    }

    private Brand getProducer() {
        return (Brand) getArguments().getSerializable(Constants.BRAND_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_brand;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectBrandFragment(this);
        notification = new Notification(view, getContext());
        productsBrandList = new ArrayList<>();
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach(getProducer().getBrand());
    }

    private void initComponents() {
        productsBrandSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeManager = new SwipeManager(productsBrandSwipeRefresh, productsBrandProgressBar,
                () -> presenter.attach(getProducer().getBrand()));
        productsBrandSwipeRefresh.setOnRefreshListener(swipeManager);
        productsBrandSwipeRefresh.setRefreshing(false);
        setLinearManager();
        productsBrandRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        linImageButton.setOnClickListener(v -> {
            setLinearManager();
            presenter.doListRecycler();
        });

        gridImageButton.setOnClickListener(v -> {
            setGridManager();
            presenter.doGridRecycler();
        });

        filters = resourceManager.getStringArray(R.array.filter);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, filters);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productsBrandSpinner.setAdapter(filterAdapter);
        productsBrandSpinner.setOnItemSelectedListener(filterListener);
    }

    private final AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            presenter.doFilter(filters[i]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    private void setLinearManager() {
        linImageButton.setEnabled(false);
        linImageButton.setBackgroundColor(resourceManager.getColor(R.color.enabledImageButtonColor));
        gridImageButton.setEnabled(true);
        viewType = AdapterViewType.LIN_TYPE;
        gridImageButton.setBackgroundColor(resourceManager.getColor(R.color.white));
        productsBrandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setGridManager() {
        gridImageButton.setEnabled(false);
        gridImageButton.setBackgroundColor(resourceManager.getColor(R.color.enabledImageButtonColor));
        linImageButton.setEnabled(true);
        viewType = AdapterViewType.GRID_TYPE;
        linImageButton.setBackgroundColor(resourceManager.getColor(R.color.white));
        productsBrandRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                Constants.SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
    }

    @Override
    public void showProducts(List<Product> products) {
        if (image_click) {
            image_click = false;
            adapter.updateData(products);
        } else {
            if (adapter != null) {
                clearRecyclerView();
            }
            productsBrandList.addAll(products);
            initAdapter();
        }
    }

    private void initAdapter() {
        adapter = new ProductsAdapter(productsBrandList,
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
                viewType
        );
        productsBrandRecyclerView.setAdapter(adapter);
    }

    private void clearRecyclerView() {
        if (!productsBrandList.isEmpty()) {
            productsBrandList.clear();
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
    public void showMessage(String message) {
        notification.showMessageWithAction(message, () -> presenter.attach(getProducer().getBrand()));
    }

    @Override
    public void showProgress() {
        productsBrandProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        productsBrandProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}




