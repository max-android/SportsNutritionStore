package ru.exampleopit111.sportsnutritionstore.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.PositionTabObj;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.category.CategoryFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.adapters.ViewPagerAdapter;
import ru.exampleopit111.sportsnutritionstore.ui.favorite.FavoriteFragment;
import ru.exampleopit111.sportsnutritionstore.ui.primary.PrimaryFragment;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Inject
    ResourceManager resourceManager;

    public MainFragment() {
    }

    public static MainFragment newInstance(PositionTabObj obj) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.TAB_POSITION, obj.getPosition());
        fragment.setArguments(args);
        return fragment;
    }

    private int getPosition() {
        return getArguments().getInt(Constants.TAB_POSITION, 0);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectMainFragment(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(PrimaryFragment.newInstance(), resourceManager.getString(R.string.main));
        adapter.addFragment(CategoryFragment.newInstance(), resourceManager.getString(R.string.category));
        adapter.addFragment(FavoriteFragment.newInstance(), resourceManager.getString(R.string.favorite));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        selectPage(getPosition());
    }

    private void selectPage(int pageIndex) {
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
