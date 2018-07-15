package ru.exampleopit111.sportsnutritionstore.ui.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.settings.SettingsPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.settings.SettingsView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.NetInspector;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.PushStatusInstance;
import ru.exampleopit111.sportsnutritionstore.ui.common.ThemeInstance;

/**
 * Created Максим on 10.06.2018.
 * Copyright © Max
 */
public class SettingsFragment extends BaseFragment implements SettingsView {

    @BindView(R.id.cleanHistorySwitchCompat)
    SwitchCompat cleanHistorySwitchCompat;
    @BindView(R.id.pushSwitchCompat)
    SwitchCompat pushSwitchCompat;
    @BindView(R.id.themeLinearLayout)
    LinearLayout themeLinearLayout;
    @BindView(R.id.themeTextView)
    TextView themeTextView;

    @Inject
    NetInspector netInspector;
    @Inject
    ResourceManager resourceManager;
    @Inject
    SettingsPresenter presenter;
    @Inject
    FirebaseMessaging firebaseMessaging;
    @Inject
    ThemeInstance themeInstance;

    private Notification notification;
    private PushStatusInstance instance;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectSettingsFragment(this);
        notification = new Notification(view, getContext());
        instance = new PushStatusInstance(getContext());
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPushSwitchStatus();
        initComponents();
    }

    private void initComponents() {
        if (!netInspector.isOnline()) {
            notification.showMessage(resourceManager.getString(R.string.сonnect_the_internet));
        }
        setNameThemeOnUI(themeInstance.getPreferences().getString(Constants.THEME_KEY, Constants.DARK_THEME));
        cleanHistorySwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) ->
                presenter.onCleanHistorySwitchChecked(isChecked));
        pushSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> onPushSwitchChecked(isChecked));
        themeLinearLayout.setOnClickListener(this::onThemeLinearLayoutClicked);
    }

    private void initPushSwitchStatus() {

        boolean switch_status = instance
                .getPreferences()
                .getBoolean(Constants.PUSH_SWITCH_KEY, false);

        pushSwitchCompat.setChecked(switch_status);
        if (switch_status) {
            firebaseMessaging.subscribeToTopic("test");
        } else {
            firebaseMessaging.unsubscribeFromTopic("test");
        }
    }

    private void onPushSwitchChecked(boolean isChecked) {

        if (isChecked) {
            firebaseMessaging.subscribeToTopic("test");
        } else {
            firebaseMessaging.unsubscribeFromTopic("test");
        }
    }


    private void onThemeLinearLayoutClicked(View view) {
        ChangeThemeDialog dialog = new ChangeThemeDialog(getContext());
        dialog.showDialog(theme -> {
                    themeInstance.setTheme(Constants.THEME_KEY, theme);
                    setNameThemeOnUI(theme);
                },
                themeInstance.getPreferences().getString(Constants.THEME_KEY, Constants.DARK_THEME)
        );
    }

    private void setNameThemeOnUI(String theme) {
        switch (theme) {
            case Constants.DARK_THEME:
                themeTextView.setText(resourceManager.getString(R.string.dark_theme));
                break;
            case Constants.LIGHT_THEME:
                themeTextView.setText(resourceManager.getString(R.string.blue_theme));
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        notification.showMessage(message);
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void showMessage() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance.setPushStatus(Constants.PUSH_SWITCH_KEY, pushSwitchCompat.isChecked());
        presenter.unbindView();
    }

}
