package ru.exampleopit111.sportsnutritionstore.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.start.StartPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.start.StartView;
import ru.exampleopit111.sportsnutritionstore.ui.main.MainActivity;

public class StartActivity extends AppCompatActivity implements StartView {

    @Inject
    StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        App.getAppComponent().injectWelcomeActivity(this);
        presenter.bindView(this);
        launchAnimation();
        presenter.attach();
    }

    private void launchAnimation() {
        Animation animIcon = AnimationUtils.loadAnimation(this, R.anim.show_view);
        findViewById(R.id.splashImage).startAnimation(animIcon);
    }

    @Override
    public void showMainScreen() {
        launchApp();
    }

    private void launchApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.intro_main, R.anim.finish_splash);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }
}
