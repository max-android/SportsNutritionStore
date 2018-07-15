package ru.exampleopit111.sportsnutritionstore.ui.registration;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.UserEntity;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.registration.MailRegistrationPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.registration.MailRegistrationView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.NetInspector;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SimpleTextChangedListener;
import ru.exampleopit111.sportsnutritionstore.ui.common.WritePermission;
import ru.exampleopit111.sportsnutritionstore.ui.custom.AvatarCircleImage;

/**
 * Created Максим on 15.06.2018.
 * Copyright © Max
 */
public class MailRegistrationFragment extends BaseFragment implements MailRegistrationView {

    @BindView(R.id.avatarImageView)
    AvatarCircleImage avatarImageView;
    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.mailEditText)
    EditText mailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.entryButton)
    Button entryButton;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.mailTextView)
    TextView mailTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.exitButton)
    Button exitButton;
    @BindView(R.id.verifLinearLayout)
    LinearLayout verifLinearLayout;
    @BindView(R.id.emailPasswordLinearLayout)
    LinearLayout emailPasswordLinearLayout;
    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;

    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    ResourceManager resourceManager;
    @Inject
    NetInspector network;
    @Inject
    WritePermission permission;
    @Inject
    RequestManager requestManager;
    @Inject
    MailRegistrationPresenter presenter;

    private String userName;
    private Notification notification;

    public MailRegistrationFragment() {
    }

    public static MailRegistrationFragment newInstance(UserEntity entity) {
        MailRegistrationFragment fragment = new MailRegistrationFragment();
        Bundle args = new Bundle();
        args.putString(Constants.USER_PHONE_NUMBER, entity.getPhone());
        fragment.setArguments(args);
        return fragment;
    }

    private String getPhoneNumber() {
        return getArguments().getString(Constants.USER_PHONE_NUMBER);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mail_registration;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectMailRegistrationFragment(this);
        presenter.bindView(this);
        notification = new Notification(view, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constants.WRITE_FILE_PERMISSION_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    notification.showMessage(getString(R.string.permission_write_granted));
                } else {
                    showMessage(resourceManager.getString(R.string.permission_write_denied));
                }
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void initComponents() {

        Observable.combineLatest(observeNameValid(),
                observeMailValid(),
                observePasswordValid(),
                (isNameValid, isMailValid, isPasswordValid) -> isNameValid && isMailValid && isPasswordValid
        ).subscribe(value -> {
            entryButton.setEnabled(value);
            registerButton.setEnabled(value);
        });
        loginLinearLayout.setLayoutTransition(new LayoutTransition());
        entryButton.setOnClickListener(this::setListener);
        registerButton.setOnClickListener(this::setListener);
        exitButton.setOnClickListener(this::setListener);
        avatarImageView.setEnabled(false);
        avatarImageView.setOnClickListener(this::imageListener);
    }

    private void setListener(View v) {

        switch (v.getId()) {
            case R.id.entryButton:
                signIn(nameEditText.getText().toString(),
                        mailEditText.getText().toString(),
                        passwordEditText.getText().toString());
                break;
            case R.id.registerButton:
                createAccount(nameEditText.getText().toString(),
                        mailEditText.getText().toString(),
                        passwordEditText.getText().toString());
                break;
            case R.id.exitButton:
                signOut();
                break;
        }
    }

    private void imageListener(View v) {
        permission.requestPermission(getContext(), this::pickImage);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.WRITE_FILE_PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.WRITE_FILE_PERMISSION_REQUEST_CODE &&
                resultCode == getActivity().RESULT_OK && data != null) {
            Uri res = data.getData();
            saveProfileIntoStorage(res);
            showProfile(res);
        }
    }

    private void saveProfileIntoStorage(Uri res) {
        if (presenter.existProfileImage()) {
            presenter.deleteProfileImage();
        }
        presenter.writeProfileImage(res.toString());
    }

    private void showProfile(Uri res) {
        requestManager.load(res)
                // .apply(RequestOptions.centerCropTransform())
                .apply(new RequestOptions()
                        .placeholder(resourceManager.getDrawable(R.drawable.ic_account_profile))
                        .centerCrop())
                // .animate(R.anim.show_view)
                // .error(R.drawable.ic_account_profile)
                .into(avatarImageView);
        animateImage(avatarImageView);
    }

    private void animateImage(AvatarCircleImage profileImage) {
        Animation animation = AnimationUtils.loadAnimation(resourceManager.getContext(),
                R.anim.show_view);
        profileImage.startAnimation(animation);
    }


    public Observable<Boolean> observeNameValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        nameEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                validationRelay.accept(!nameEditText.getText().toString().trim().isEmpty());
            }
        });
        return validationRelay;
    }

    public Observable<Boolean> observeMailValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        mailEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                String emailString = mailEditText.getText().toString();
                validationRelay.accept(!emailString.trim().isEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()
                );
            }
        });
        return validationRelay;
    }

    public Observable<Boolean> observePasswordValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        passwordEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                validationRelay.accept(!passwordEditText.getText().toString().trim().isEmpty() &&
                        passwordEditText.getText().toString().length() >= 6
                );
            }
        });
        return validationRelay;
    }

    //Войти,если ранее зарегистрировались
    private void signIn(String userName, String email, String password) {
//        dialogProgress.showProgressDialog();
        if (network.isOnline()) {
            presenter.saveProfile(userName);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::signInUser);
        } else {
            showMessage(resourceManager.getString(R.string.not_network));
        }
    }

    private void createAccount(String userName, String email, String password) {
        // dialogProgress.showProgressDialog();
        if (network.isOnline()) {
            presenter.saveProfile(userName);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::createUser);
        } else {
            showMessage(resourceManager.getString(R.string.not_network));
        }
    }

    private void createUser(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            updateUI(user);
        } else {
            showMessage(resourceManager.getString(R.string.authentication_failed));
            updateUI(null);
        }
        // dialogProgress.hideProgressDialog();
    }

    private void signInUser(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            updateUI(user);
        } else {
            showMessage(resourceManager.getString(R.string.authentication_failed));
            updateUI(null);
        }
        // dialogProgress.hideProgressDialog();
    }

    //выйти из регистрации
    private void signOut() {
        if (network.isOnline()) {
            firebaseAuth.signOut();
            updateUI(null);
        } else {
            showMessage(resourceManager.getString(R.string.not_network));
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        // dialogProgress.hideProgressDialog();
        if (currentUser != null) {
            presenter.getProfile();
            emailPasswordLinearLayout.setVisibility(View.GONE);
            verifLinearLayout.setVisibility(View.VISIBLE);
            nameTextView.setText(userName);
            mailTextView.setText(currentUser.getEmail());
            phoneTextView.setText(getPhoneNumber());
            avatarImageView.setEnabled(true);
            if (presenter.existProfileImage()) {
                presenter.getImageProfileUrl();
            }

        } else {
            verifLinearLayout.setVisibility(View.GONE);
            avatarImageView.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_account_profile));
            emailPasswordLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setImageProfile(String url) {
        Uri uri = Uri.parse(url);
        showProfile(uri);
    }

    @Override
    public void setNameProfile(String name) {
        userName = name;
    }

    private void showMessage(String message) {
        notification.showMessage(message);
    }
}

