package ru.exampleopit111.sportsnutritionstore.ui.registration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.PhoneRegistrationPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.registration.PhoneRegistrationView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.custom.FormEditText;

/**
 * Created Максим on 15.06.2018.
 * Copyright © Max
 */
public class PhoneRegistrationFragment extends BaseFragment implements PhoneRegistrationView {

    @BindView(R.id.phoneEditText)
    FormEditText phoneEditText;
    @BindView(R.id.codeEditText)
    FormEditText codeEditText;
    @BindView(R.id.timerValueView)
    TextView timerValueView;
    @BindView(R.id.receiveTextView)
    TextView receiveTextView;
    @BindView(R.id.codeErrorTextView)
    TextView codeErrorTextView;
    @BindView(R.id.loginButton)
    Button loginButton;

    @Inject
    ResourceManager resourceManager;
    @Inject
    PhoneRegistrationPresenter presenter;

    public PhoneRegistrationFragment() {
    }

    public static PhoneRegistrationFragment newInstance() {
        PhoneRegistrationFragment fragment = new PhoneRegistrationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_phone_registration;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectPhoneRegistrationFragment(this);
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    @SuppressLint("CheckResult")
    private void initComponents() {
        codeErrorTextView.setVisibility(View.GONE);
        phoneEditText.observeValid()
                .subscribe(isValid -> {
                    receiveTextView.setEnabled(isValid);
                    codeEditText.setEnabled(isValid);
                    timerValueView.setEnabled(isValid);
                });
        receiveTextView.setOnClickListener(v -> {
            presenter.startTimer();
            presenter.onSendCodeSms(phoneEditText.getText().toString());
        });

        codeEditText.observeErrorState()
                .subscribe(isError -> codeErrorTextView
                        .setVisibility(isError ? View.VISIBLE : View.GONE));

        Observable.combineLatest(
                phoneEditText.observeValid(),
                codeEditText.observeValid(),
                (isPhoneValid, isCodeValid) -> isPhoneValid && isCodeValid
        )
                .subscribe(loginButton::setEnabled);


        loginButton.setOnClickListener(v -> presenter.onMailRegisterClicked(
                phoneEditText.getText().toString(),
                codeEditText.getText().toString()
        ));
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopTimer();
        presenter.unbindView();
    }

    @Override
    public void setTime(String time) {
        timerValueView.setText(String.format("%d%s", Integer.parseInt(time),
                resourceManager.getString(R.string.seconds_text)));
    }

    @Override
    public void showButtonSend() {
        timerValueView.setVisibility(View.INVISIBLE);
        receiveTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTimer() {
        timerValueView.setVisibility(View.VISIBLE);
        receiveTextView.setVisibility(View.INVISIBLE);
    }

}
