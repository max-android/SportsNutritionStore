package ru.exampleopit111.sportsnutritionstore.ui.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import ru.exampleopit111.sportsnutritionstore.model.entities.common.SearchEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.search.SearchPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.search.SearchView;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.AdapterViewType;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.ProductsAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SwipeManager;

/**
 * Created Максим on 30.06.2018.
 * Copyright © Max
 */
public class SearchFragment extends BaseFragment implements SearchView {

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    @BindView(R.id.searchProgressBar)
    ProgressBar searchProgressBar;
    @BindView(R.id.searchSwipeRefresh)
    SwipeRefreshLayout searchSwipeRefresh;
    @BindView(R.id.searchSpinner)
    Spinner searchSpinner;
    @BindView(R.id.gridImageButton)
    ImageButton gridImageButton;
    @BindView(R.id.linImageButton)
    ImageButton linImageButton;
    @BindView(R.id.entireSearchConstr)
    ConstraintLayout entireSearchConstr;
    @BindView(R.id.emptySearchLinearLayout)
    LinearLayout emptySearchLinearLayout;

    private AdapterViewType viewType;
    private String[] filters;

    @Inject
    ResourceManager resourceManager;
    @Inject
    SearchPresenter presenter;
    @Inject
    RequestManager requestManager;

    private Notification notification;
    private SwipeManager swipeManager;
    private List<Product> searchList;
    private ProductsAdapter adapter;
    private boolean image_click;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(SearchEntity searchEntity) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(Constants.SEARCH_KEY, searchEntity.getSearch());
        fragment.setArguments(args);
        return fragment;
    }

    private String getSearch() {
        return getArguments().getString(Constants.SEARCH_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectSearchFragment(this);
        presenter.bindView(this);
        searchList = new ArrayList<>();
        notification = new Notification(view, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        presenter.attach(getSearch());
    }

    private void initComponents() {
        searchSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeManager = new SwipeManager(searchSwipeRefresh, searchProgressBar,
                () -> presenter.attach(getSearch()));
        searchSwipeRefresh.setOnRefreshListener(swipeManager);
        searchSwipeRefresh.setRefreshing(false);
        setLinearManager();
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
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
        searchSpinner.setAdapter(filterAdapter);
        searchSpinner.setOnItemSelectedListener(filterListener);
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
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setGridManager() {
        gridImageButton.setEnabled(false);
        gridImageButton.setBackgroundColor(resourceManager.getColor(R.color.enabledImageButtonColor));
        linImageButton.setEnabled(true);
        viewType = AdapterViewType.GRID_TYPE;
        linImageButton.setBackgroundColor(resourceManager.getColor(R.color.white));
        searchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                Constants.SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
    }

    @Override
    public void showProducts(List<Product> products) {
        if (products.isEmpty()) {
            setEmptyLayout();
        } else {
            if (image_click) {
                image_click = false;
                adapter.updateData(products);
            } else {
                if (adapter != null) {
                    clearRecyclerView();
                }
                searchList.addAll(products);
                initAdapter();
            }
        }
    }

    private void initAdapter() {
        adapter = new ProductsAdapter(searchList,
                resourceManager,
                product ->
                        presenter.onProductClicked(product),
                requestManager,
                product -> {
                    presenter.onBasketClicked(product, getSearch());
                    image_click = true;
                    notifAboutUpdateBasket(product.isBasket());
                },
                product -> {
                    presenter.onFavotiteClicked(product, getSearch());
                    image_click = true;
                    notifAboutUpdateFavorite(product.isFavorite());
                },
                viewType
        );
        searchRecyclerView.setAdapter(adapter);
    }

    private void checkEmptinessOfList(List<Product> products) {
        if (products.isEmpty()) {
            setEmptyLayout();
        }
    }

    private void clearRecyclerView() {
        if (!searchList.isEmpty()) {
            searchList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void setEmptyLayout() {
        entireSearchConstr.setVisibility(View.GONE);
        emptySearchLinearLayout.setVisibility(View.VISIBLE);
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
        setEmptyLayout();
        notification.showMessage(message);
    }

    @Override
    public void showProgress() {
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        setEmptyLayout();
        notification.showMessage(error);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
