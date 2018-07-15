package ru.exampleopit111.sportsnutritionstore.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    @BindView(R.id.mainToolBar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_main2);
        App.getAppComponent().injectToolbarActivity(this);
        ButterKnife.bind(this);
    }

    public void attachToolbar(String caption, boolean arrows) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(caption);
        final ActionBar ab = getSupportActionBar();
    }
}
