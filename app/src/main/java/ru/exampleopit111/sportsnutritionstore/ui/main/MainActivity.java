package ru.exampleopit111.sportsnutritionstore.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.mikepenz.materialdrawer.holder.BadgeStyle;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.PositionTabObj;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.SearchEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.main.MainPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.main.MainView;
import ru.exampleopit111.sportsnutritionstore.ui.about_app.AboutAppFragment;
import ru.exampleopit111.sportsnutritionstore.ui.base.NavigateActivity;
import ru.exampleopit111.sportsnutritionstore.ui.basket.BasketFragment;
import ru.exampleopit111.sportsnutritionstore.ui.brand.BrandFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.exampleopit111.sportsnutritionstore.ui.common.ThemeInstance;
import ru.exampleopit111.sportsnutritionstore.ui.custom.AvatarCircleImage;
import ru.exampleopit111.sportsnutritionstore.ui.map.MapFragment;
import ru.exampleopit111.sportsnutritionstore.ui.method_courier.MethodCourierFragment;
import ru.exampleopit111.sportsnutritionstore.ui.method_receiving_order.MethodReceivingOrderFragment;
import ru.exampleopit111.sportsnutritionstore.ui.products_category.ProductsCategoryFragment;
import ru.exampleopit111.sportsnutritionstore.ui.registration.LoginActivity;
import ru.exampleopit111.sportsnutritionstore.ui.search.SearchFragment;
import ru.exampleopit111.sportsnutritionstore.ui.self_delivery_method.SelfDeliveryMethodFragment;
import ru.exampleopit111.sportsnutritionstore.ui.settings.SettingsFragment;
import ru.exampleopit111.sportsnutritionstore.ui.sports_nutrition_product.ProductDescriptionFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class MainActivity extends NavigateActivity implements MainView {

    @Inject
    ResourceManager resourceManager;
    @Inject
    Router router;
    @Inject
    RequestManager requestManager;
    @Inject
    MainPresenter presenter;
    @Inject
    ThemeInstance themeInstance;

    private SearchView searchView;
    private boolean flag_show_menu = true;
    private ProductCategory selectCategory;
    private Brand selectBrand;
    private String description_come_from;
    private SearchEntity selectSearch;
    private TextView busketTextView;
    private FrameLayout busketCounterFrame;
    private BasketEntity actualBasketEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().injectMainActivity(this);
        setAppTheme(themeInstance.getPreferences().getString(Constants.THEME_KEY, Constants.DARK_THEME));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.bindView(this);
        presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        initComponents();
        initProfile();
    }

    @Override
    protected void onStart() {
        presenter.initProfile();
        super.onStart();
    }

    private void setAppTheme(String theme) {
        switch (theme) {
            case Constants.DARK_THEME:
                setTheme(R.style.AppTheme);
                break;
            case Constants.LIGHT_THEME:
                setTheme(R.style.AppThemeLight);
                break;
        }
    }

    private void initComponents() {
        setupDrawer();
        drawer.getHeader().setOnClickListener(v -> showRegistration());
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        drawer.setOnDrawerItemClickListener(
                (view, position, drawerItem) -> {
                    switch (position) {
                        case 1:
                            presenter.onClickMain(position - 1);
                            break;
                        case 2:
                            presenter.onClickMain(position - 1);
                            break;
                        case 3:
                            presenter.onClickBasket();
                            break;
                        case 4:
                            presenter.onClickMain(position - 2);
                            break;
                        case 5:
                            presenter.onClickLocationStores();
                            break;
                        case 6:
                            presenter.onClickSettings();
                            break;
                        case 8:
                            showRegistration();
                            break;
                        case 9:
                            presenter.onClickAboutApp();
                            break;
                        default:
                            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
                            break;
                    }
                    return false;
                }
        );
    }

    private void showRegistration() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initProfile() {
        presenter.initProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        settingSearchItem(menu);
        settingBasketItem(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void settingSearchItem(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(resourceManager.getString(R.string.search_titul));
        searchView.setDrawingCacheBackgroundColor(Color.WHITE);
        searchView.setOnQueryTextListener(queryTextListener);
    }

    private void settingBasketItem(Menu menu) {
        MenuItem basketItem = menu.findItem(R.id.action_basket);
        FrameLayout rootView = (FrameLayout) basketItem.getActionView();
        busketCounterFrame = (FrameLayout) rootView.findViewById(R.id.busketCounterFrame);
        busketTextView = (TextView) rootView.findViewById(R.id.busketTextView);
        rootView.setOnClickListener(v -> onOptionsItemSelected(basketItem));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        initDataDrawer();
        if (flag_show_menu) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initDataDrawer() {
        presenter.initCountOrdersBasket();
        presenter.initCountFavoriteProducts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_basket:
                presenter.onClickBasket();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setCountOrdersBusket(List<Product> products) {
        visibleCountProductsInBasket(products);
    }

    private void visibleCountProductsInBasket(List<Product> products) {
        int count = products.size();
        busketCounterFrame.setVisibility((count > 0) ? View.VISIBLE : View.GONE);
        busketTextView.setText(String.valueOf(count));
        itemBasket.withBadge(String.valueOf(count))
                .withBadgeStyle(new BadgeStyle().withTextColor(resourceManager.getColor(R.color.colorText)));
        drawer.updateItem(itemBasket);
    }

    @Override
    public void setCountFavoriteProducts(List<Product> products) {
        visibleCountProductsInFavorites(products);
    }

    private void visibleCountProductsInFavorites(List<Product> products) {
        int count = products.size();
        itemFavorite.withBadge(String.valueOf(count))
                .withBadgeStyle(new BadgeStyle().withTextColor(resourceManager.getColor(R.color.colorText)));
        drawer.updateItem(itemFavorite);
    }

    @Override
    public void setProfile(String email, String phone, String url) {
        initProfileView(email, phone, url);
    }

    private void initProfileView(String email, String phone, String url) {
        ViewGroup viewGroup = (ViewGroup) drawer.getHeader();
        TextView mailTextView = (TextView) viewGroup.getChildAt(1);
        AvatarCircleImage profileImage = (AvatarCircleImage) viewGroup.getChildAt(0);
        TextView phoneTextView = (TextView) viewGroup.getChildAt(2);
        if (email.length() == 0) {
            mailTextView.setText(resourceManager.getString(R.string.entry_registr));
            profileImage.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_account_profile));
            phoneTextView.setText(resourceManager.getString(R.string.empty));
        } else {
            if (url.equals(resourceManager.getString(R.string.default_profile))) {
                profileImage.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_account_profile));
            } else {
                requestManager.load(url)
                        //.apply(RequestOptions.centerCropTransform())
                        .apply(new RequestOptions()
                                .placeholder(resourceManager.getDrawable(R.drawable.ic_account_profile))
                                .centerCrop())
                        // .animate(R.anim.show_view)
                        .into(profileImage);
                animateImage(profileImage);

            }
            mailTextView.setText(email);
            phoneTextView.setText(phone);
        }
    }

    private void animateImage(AvatarCircleImage profileImage) {
        Animation animation = AnimationUtils.loadAnimation(resourceManager.getContext(),
                R.anim.show_view);
        profileImage.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }

        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.setIconified(true);
        }

        if (fragment instanceof MainFragment) {
            super.onBackPressed();
        }

        if (fragment instanceof BasketFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof MapFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof SettingsFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof AboutAppFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof SearchFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof ProductsCategoryFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION_CATEGORY);
        }

        if (fragment instanceof MethodReceivingOrderFragment) {
            presenter.onClickBasket();
        }

        if (fragment instanceof MethodCourierFragment) {
            presenter.onClickReceivingOrder(actualBasketEntity);
        }

        if (fragment instanceof SelfDeliveryMethodFragment) {
            presenter.onClickReceivingOrder(actualBasketEntity);
        }

        if (fragment instanceof BrandFragment) {
            presenter.onClickMain(Constants.MAIN_TAB_POSITION);
        }

        if (fragment instanceof ProductDescriptionFragment) {

            switch (description_come_from) {
                case Screens.MAIN_FRAGMENT:
                    presenter.onClickMain(Constants.MAIN_TAB_POSITION_FAVORITE);
                    break;
                case Screens.BASKET_FRAGMENT:
                    presenter.onClickBasket();
                    break;
                case Screens.PRODUCTS_CATEGORY_FRAGMENT:
                    presenter.onClickCategory(selectCategory);
                    break;
                case Screens.SEARCH_FRAGMENT:
                    presenter.onClickSearch(selectSearch);
                    break;
                case Screens.BRAND_FRAGMENT:
                    presenter.onClickBrand(selectBrand);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
        presenter.closeResources();
    }

    @Override
    protected Navigator getNavigator() {
        return navigator;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            presenter.onClickSearch(new SearchEntity(query));
            searchView.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private final Navigator navigator = new SupportAppNavigator(this, R.id.mainContainer) {
        /**
         * Фабрика интентов для запуска других активностей из текущей
         *
         * @param screenKey идентификатор экрана активити
         * @param data      объект с данными, который приводится к нужному типу и передается в createIntent() активности
         * @return интент для запуска активности; null если не будет перехода на другую активность
         */
        @Override
        protected Intent createActivityIntent(
                @NonNull final Context context,
                @NonNull final String screenKey,
                @Nullable final Object data
        ) {
            return null;
        }

        /**
         * Фабрика фрагментов для текущей активности
         * @param screenKey идентификатор фрагментов
         * @param data объект с данными, который приводится к нужному типу и передается в newInstance() фрагмента
         * @return фрагмент, соответствующий {@code screenKey}
         */
        @Override
        protected Fragment createFragment(@NonNull final String screenKey, @Nullable final Object data) {
            Fragment fragment = null;
            switch (screenKey) {
                case Screens.MAIN_FRAGMENT:
                    if (data != null) {
                        fragment = MainFragment.newInstance((PositionTabObj) data);
                        description_come_from = Screens.MAIN_FRAGMENT;
                        attachToolbar(resourceManager.getString(R.string.empty), false);
                        setArrowMainNavigation();
                    }
                    break;

                case Screens.BASKET_FRAGMENT:
                    fragment = BasketFragment.newInstance();
                    description_come_from = Screens.BASKET_FRAGMENT;
                    attachToolbar(resourceManager.getString(R.string.basket), true);
                    setArrowSecondaryNavigation(Screens.BASKET_FRAGMENT);
                    break;

                case Screens.MAP_FRAGMENT:
                    fragment = MapFragment.newInstance();
                    attachToolbar(resourceManager.getString(R.string.address), true);
                    setArrowSecondaryNavigation(Screens.MAP_FRAGMENT);
                    break;

                case Screens.SETTINGS_FRAGMENT:
                    fragment = SettingsFragment.newInstance();
                    attachToolbar(resourceManager.getString(R.string.setting), true);
                    setArrowSecondaryNavigation(Screens.SETTINGS_FRAGMENT);
                    break;

                case Screens.ABOUT_APP_FRAGMENT:
                    fragment = AboutAppFragment.newInstance();
                    attachToolbar(resourceManager.getString(R.string.about_app), true);
                    setArrowSecondaryNavigation(Screens.ABOUT_APP_FRAGMENT);
                    break;

                case Screens.PRODUCTS_CATEGORY_FRAGMENT:
                    if (data != null) {
                        fragment = ProductsCategoryFragment.newInstance((ProductCategory) data);
                        description_come_from = Screens.PRODUCTS_CATEGORY_FRAGMENT;
                        selectCategory = (ProductCategory) data;
                        attachToolbar(((ProductCategory) data).getCategory(), true);
                        setArrowSecondaryNavigation(Screens.PRODUCTS_CATEGORY_FRAGMENT);
                    }
                    break;

                case Screens.PRODUCT_DESCRIPTION_FRAGMENT:
                    if (data != null) {
                        fragment = ProductDescriptionFragment.newInstance((Product) data);
                        attachToolbar(((Product) data).getName(), true);
                        setArrowSecondaryNavigation(Screens.PRODUCT_DESCRIPTION_FRAGMENT);
                    }
                    break;

                case Screens.SEARCH_FRAGMENT:
                    if (data != null) {
                        fragment = SearchFragment.newInstance((SearchEntity) data);
                        description_come_from = Screens.SEARCH_FRAGMENT;
                        selectSearch = (SearchEntity) data;
                        attachToolbar(((SearchEntity) data).getSearch(), true);
                        setArrowSearchNavigation(Screens.SEARCH_FRAGMENT);
                    }
                    break;

                case Screens.METHOD_RECEIVING_ORDER_FRAGMENT:
                    if (data != null) {
                        fragment = MethodReceivingOrderFragment.newInstance((BasketEntity) data);
                        actualBasketEntity = (BasketEntity) data;
                        attachToolbar(resourceManager.getString(R.string.method_of_receiving_an_order), true);
                        setArrowSecondaryNavigation(Screens.METHOD_RECEIVING_ORDER_FRAGMENT);
                    }
                    break;

                case Screens.METHOD_COURIER_FRAGMENT:
                    if (data != null) {
                        fragment = MethodCourierFragment.newInstance((BasketEntity) data);
                        attachToolbar(resourceManager.getString(R.string.method_courier), true);
                        setArrowSecondaryNavigation(Screens.METHOD_COURIER_FRAGMENT);
                    }
                    break;

                case Screens.SELF_DELIVERY_FRAGMENT:
                    if (data != null) {
                        fragment = SelfDeliveryMethodFragment.newInstance((BasketEntity) data);
                        attachToolbar(resourceManager.getString(R.string.method_self_delivery), true);
                        setArrowSecondaryNavigation(Screens.SELF_DELIVERY_FRAGMENT);
                    }
                    break;

                case Screens.BRAND_FRAGMENT:
                    if (data != null) {
                        fragment = BrandFragment.newInstance((Brand) data);
                        description_come_from = Screens.BRAND_FRAGMENT;
                        selectBrand = (Brand) data;
                        attachToolbar(((Brand) data).getBrand(), true);
                        setArrowSecondaryNavigation(Screens.BRAND_FRAGMENT);
                    }
                    break;

                default:
                    if (data != null) {
                        fragment = MainFragment.newInstance((PositionTabObj) data);
                        description_come_from = Screens.MAIN_FRAGMENT;
                        attachToolbar(resourceManager.getString(R.string.empty), false);
                        setArrowMainNavigation();
                    }
                    break;
            }
            return fragment;
        }

        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);

        }

        @Override
        protected void setupFragmentTransactionAnimation(
                Command command,
                Fragment currentFragment,
                Fragment nextFragment,
                FragmentTransaction fragmentTransaction) {

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            View v = getLayoutInflater().inflate(R.layout.fragment_product_description, null);
            final View image = v.findViewById(R.id.productImageView);
            fragmentTransaction.addSharedElement(image,
                    resourceManager.getString(R.string.transition_product));
        }

        private void setArrowMainNavigation() {
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
            toolbar.setNavigationOnClickListener(v -> drawer.openDrawer());
            flag_show_menu = true;
            supportInvalidateOptionsMenu();
        }

        private void setArrowSecondaryNavigation(String screen) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
            navigationToolBar(screen);
            flag_show_menu = false;
            supportInvalidateOptionsMenu();
        }

        private void setArrowSearchNavigation(String screen) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
            navigationToolBar(screen);
            flag_show_menu = true;
            supportInvalidateOptionsMenu();
        }

        private void navigationToolBar(String screen) {
            switch (screen) {
                case Screens.BASKET_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.MAP_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.SETTINGS_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.ABOUT_APP_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.PRODUCTS_CATEGORY_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION_CATEGORY));
                    break;

                case Screens.PRODUCT_DESCRIPTION_FRAGMENT:

                    switch (description_come_from) {
                        case Screens.MAIN_FRAGMENT:
                            toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION_FAVORITE));
                            break;
                        case Screens.BASKET_FRAGMENT:
                            toolbar.setNavigationOnClickListener(exit -> presenter.onClickBasket());
                            break;
                        case Screens.PRODUCTS_CATEGORY_FRAGMENT:
                            toolbar.setNavigationOnClickListener(exit -> presenter.onClickCategory(selectCategory));
                            break;
                        case Screens.SEARCH_FRAGMENT:
                            toolbar.setNavigationOnClickListener(exit -> presenter.onClickSearch(selectSearch));
                            break;
                        case Screens.BRAND_FRAGMENT:
                            toolbar.setNavigationOnClickListener(exit -> presenter.onClickBrand(selectBrand));
                            break;
                    }
                    break;

                case Screens.SEARCH_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.BRAND_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;

                case Screens.METHOD_RECEIVING_ORDER_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickBasket());
                    break;

                case Screens.METHOD_COURIER_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickReceivingOrder(actualBasketEntity));
                    break;

                case Screens.SELF_DELIVERY_FRAGMENT:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickReceivingOrder(actualBasketEntity));
                    break;

                default:
                    toolbar.setNavigationOnClickListener(exit -> presenter.onClickMain(Constants.MAIN_TAB_POSITION));
                    break;
            }

        }

    };

}
