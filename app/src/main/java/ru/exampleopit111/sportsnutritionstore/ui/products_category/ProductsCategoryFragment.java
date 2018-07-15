package ru.exampleopit111.sportsnutritionstore.ui.products_category;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.products_category.ProductsCategoryPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.products_category.ProductsCategoryView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.AdapterViewType;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.ProductsAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class ProductsCategoryFragment extends BaseFragment implements ProductsCategoryView {

    @BindView(R.id.prodCategoryRecyclerView)
    RecyclerView prodCategoryRecyclerView;
    @BindView(R.id.prodCategoryProgressBar)
    ProgressBar prodCategoryProgressBar;
    @BindView(R.id.prodCategorySwipeRefresh)
    SwipeRefreshLayout prodCategorySwipeRefresh;
    @BindView(R.id.prodCategorySpinner)
    Spinner prodCategorySpinner;
    @BindView(R.id.gridImageButton)
    ImageButton gridImageButton;
    @BindView(R.id.linImageButton)
    ImageButton linImageButton;

    @Inject
    ResourceManager resourceManager;
    @Inject
    ProductsCategoryPresenter presenter;
    @Inject
    RequestManager requestManager;

    private AdapterViewType viewType;
    private String[] filters;
    private Notification notification;
    private SwipeManager swipeManager;
    private List<Product> productsCategoryList;
    private ProductsAdapter adapter;
    private boolean image_click;

    public ProductsCategoryFragment() {
    }

    public static ProductsCategoryFragment newInstance(ProductCategory category) {
        ProductsCategoryFragment fragment = new ProductsCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    private ProductCategory getCategory() {
        return (ProductCategory) getArguments().getSerializable(Constants.CATEGORY_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_products_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectProductsCategoryFragment(this);
        notification = new Notification(view, getContext());
        productsCategoryList = new ArrayList<>();
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach(getCategory());
    }

    private void initComponents() {
        prodCategorySwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeManager = new SwipeManager(prodCategorySwipeRefresh, prodCategoryProgressBar,
                () -> presenter.attach(getCategory()));
        prodCategorySwipeRefresh.setOnRefreshListener(swipeManager);
        prodCategorySwipeRefresh.setRefreshing(false);
        setLinearManager();
        prodCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
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
        prodCategorySpinner.setAdapter(filterAdapter);
        prodCategorySpinner.setOnItemSelectedListener(filterListener);
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
        prodCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setGridManager() {
        gridImageButton.setEnabled(false);
        gridImageButton.setBackgroundColor(resourceManager.getColor(R.color.enabledImageButtonColor));
        linImageButton.setEnabled(true);
        viewType = AdapterViewType.GRID_TYPE;
        linImageButton.setBackgroundColor(resourceManager.getColor(R.color.white));
        prodCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
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
            productsCategoryList.addAll(products);
            initAdapter(productsCategoryList);
        }
    }

    private void initAdapter(List<Product> products) {
        adapter = new ProductsAdapter(productsCategoryList,
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
        prodCategoryRecyclerView.setAdapter(adapter);
    }

    private void clearRecyclerView() {
        if (!productsCategoryList.isEmpty()) {
            productsCategoryList.clear();
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
        notification.showMessageWithAction(message, () -> presenter.attach(getCategory()));
    }

    @Override
    public void showProgress() {
        prodCategoryProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        prodCategoryProgressBar.setVisibility(View.GONE);
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
