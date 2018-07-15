package ru.exampleopit111.sportsnutritionstore.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.UserEntity;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.LoginActivityPresenter;
import ru.exampleopit111.sportsnutritionstore.ui.base.NavigateActivity;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.exampleopit111.sportsnutritionstore.ui.common.ThemeInstance;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class LoginActivity extends NavigateActivity {

    @Inject
    Router router;
    @Inject
    LoginActivityPresenter presenter;
    @Inject
    ThemeInstance themeInstance;

    @Override
    protected Navigator getNavigator() {
        return navigator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().injectLoginActivity(this);
        setAppTheme(themeInstance.getPreferences().getString(Constants.THEME_KEY, Constants.DARK_THEME));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter.launchScreen();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    private final Navigator navigator = new SupportAppNavigator(this, R.id.loginContainer) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            Fragment fragment = null;
            switch (screenKey) {

                case Screens.PHONE_REG_FRAGMENT:
                    fragment = PhoneRegistrationFragment.newInstance();
                    break;

                case Screens.MAIL_REG_FRAGMENT:
                    if (data != null)
                        fragment = MailRegistrationFragment.newInstance((UserEntity) data);
                    break;

                default:
                    fragment = PhoneRegistrationFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        protected Intent createActivityIntent(Context context, String screenKey, Object data) {
            return null;
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
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        }
    };

}
