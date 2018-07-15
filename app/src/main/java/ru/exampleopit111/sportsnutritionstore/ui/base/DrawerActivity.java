package ru.exampleopit111.sportsnutritionstore.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public class DrawerActivity extends ToolbarActivity {

    @Inject
    ResourceManager resourceManager;
    public Drawer drawer;
    public PrimaryDrawerItem itemBasket;
    public PrimaryDrawerItem itemFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.getAppComponent().injectDrawerActivity(this);
        super.onCreate(savedInstanceState);
    }

    public void setupDrawer() {
        itemBasket = new PrimaryDrawerItem().withName(resourceManager.getString(R.string.basket).toUpperCase())
                .withIcon(resourceManager.getDrawable(R.drawable.ic_basket))
                .withIdentifier(3);

        itemFavorite = new PrimaryDrawerItem().withName(resourceManager.getString(R.string.favorite).toUpperCase())
                .withIcon(resourceManager.getDrawable(R.drawable.ic_favorite))
                .withIdentifier(4);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withToolbar(toolbar)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withHeader(R.layout.drawer_header)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) DrawerActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager
                                .hideSoftInputFromWindow(DrawerActivity.this.getCurrentFocus()
                                        .getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                    }
                })
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.main).toUpperCase())
                                .withIcon(resourceManager.getDrawable(R.drawable.ic_home))
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.category).toUpperCase())
                                .withIcon(resourceManager.getDrawable(R.drawable.ic_category))
                                .withIdentifier(2),
                        itemBasket,
                        itemFavorite,
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.address).toUpperCase())
                                .withIcon(resourceManager.getDrawable(R.drawable.ic_map))
                                .withIdentifier(5),
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.setting).toUpperCase())
                                .withIcon(resourceManager.getDrawable(R.drawable.ic_settings))
                                .withIdentifier(6),
                        new DividerDrawerItem().withIdentifier(7),
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.my_profile).toUpperCase())
                                .withIdentifier(8),
                        new PrimaryDrawerItem().withName(resourceManager.getString(R.string.about_app).toUpperCase())
                                .withIdentifier(9)
                )
                .build();
    }
}
