package ru.exampleopit111.sportsnutritionstore.ui.method_courier;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.OrderRequestEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.UserAddress;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_courier.MethodCourierPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.method_courier.MethodCourierView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.common.SimpleTextChangedListener;
import ru.exampleopit111.sportsnutritionstore.ui.custom.CustomDialog;
import ru.exampleopit111.sportsnutritionstore.ui.custom.FormEditText;

/**
 * Created Максим on 05.07.2018.
 * Copyright © Max
 */
public class MethodCourierFragment extends BaseFragment implements MethodCourierView {

    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.streetEditText)
    EditText streetEditText;
    @BindView(R.id.houseEditText)
    EditText houseEditText;
    @BindView(R.id.apartmentEditText)
    EditText apartamentEditText;
    @BindView(R.id.courierLinearLayout)
    LinearLayout courierLinearLayout;
    @BindView(R.id.datePickerLinearLayout)
    LinearLayout datePickerLinearLayout;
    @BindView(R.id.timePickerLinearLayout)
    LinearLayout timePickerLinearLayout;
    @BindView(R.id.inCashCheckBox)
    CheckBox inCashCheckBox;
    @BindView(R.id.cardCheckBox)
    CheckBox cardCheckBox;
    @BindView(R.id.commentsEditText)
    EditText commentsEditText;
    @BindView(R.id.totalCoastTextView)
    TextView totalCoastTextView;
    @BindView(R.id.checkoutButton)
    Button checkoutButton;
    @BindView(R.id.numberCardEditText)
    FormEditText numberCardEditText;
    @BindView(R.id.cardLinearLayout)
    LinearLayout cardLinearLayout;

    @Inject
    MethodCourierPresenter presenter;
    @Inject
    ResourceManager resourceManager;

    private String payment_type;
    private Calendar dateAndTime;
    private String selectDateTime;
    private Notification notification;

    public MethodCourierFragment() {
    }

    public static MethodCourierFragment newInstance(BasketEntity basketEntity) {
        MethodCourierFragment fragment = new MethodCourierFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BASKET_KEY, basketEntity);
        fragment.setArguments(args);
        return fragment;
    }

    private BasketEntity getCourierEntity() {
        return (BasketEntity) getArguments().getSerializable(Constants.BASKET_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_method_courier;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectMethodCourierFragment(this);
        notification = new Notification(view, getContext());
        presenter.bindView(this);
        presenter.attach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    @SuppressLint("CheckResult")
    private void initComponents() {
        payment_type = Constants.TYPE_EMPTY;
        selectDateTime = resourceManager.getString(R.string.default_date_time);
        dateAndTime = Calendar.getInstance();
        cardLinearLayout.setLayoutTransition(new LayoutTransition());
        totalCoastTextView.setText(getCourierEntity().getBasketPrice());
        datePickerLinearLayout.setOnClickListener(this::setDate);
        timePickerLinearLayout.setOnClickListener(this::setTime);

        Observable.combineLatest(
                observeStreetValid(),
                observeHouseValid(),
                observeApartmentValid(),
                (isStreetValid, isHouseValid, isApartmentValid) ->
                        isStreetValid && isHouseValid && isApartmentValid
        ).subscribe(checkoutButton::setEnabled);

        inCashCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cardCheckBox.setChecked(false);
                cardLinearLayout.setVisibility(View.GONE);
                payment_type = Constants.TYPE_INCASH;
            } else {
                cardCheckBox.setChecked(true);
            }
        });
        cardCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                inCashCheckBox.setChecked(false);
                cardLinearLayout.setVisibility(View.VISIBLE);
                payment_type = Constants.TYPE_CARD;
            } else {
                inCashCheckBox.setChecked(true);
                cardLinearLayout.setVisibility(View.GONE);
            }
        });
        checkoutButton.setOnClickListener(this::onClickCheckout);
    }


    private void onClickCheckout(View view) {
        String comment = "";
        if (commentsEditText.getText() != null && !commentsEditText.getText().toString().isEmpty()) {
            comment = commentsEditText.getText().toString();
        } else {
            comment = resourceManager.getString(R.string.default_comment);
        }

        switch (payment_type) {
            case Constants.TYPE_INCASH:
                presenter.sendOrderRequest(new OrderRequestEntity(payment_type,
                        nameTextView.getText().toString(),
                        phoneTextView.getText().toString(),
                        comment,
                        totalCoastTextView.getText().toString(),
                        new UserAddress(streetEditText.getText().toString(),
                                Integer.parseInt(houseEditText.getText().toString()),
                                Integer.parseInt(apartamentEditText.getText().toString())),
                        selectDateTime
                ));
                break;

            case Constants.TYPE_CARD:
                if (!numberCardEditText.getText().toString().trim().isEmpty() &&
                        numberCardEditText.getText().toString().length() == 16) {
                    presenter.sendOrderRequest(new OrderRequestEntity(payment_type,
                            nameTextView.getText().toString(),
                            phoneTextView.getText().toString(),
                            comment,
                            totalCoastTextView.getText().toString(),
                            numberCardEditText.getText().toString(),
                            new UserAddress(streetEditText.getText().toString(),
                                    Integer.parseInt(houseEditText.getText().toString()),
                                    Integer.parseInt(apartamentEditText.getText().toString())),
                            selectDateTime
                    ));
                } else {
                    notification.showMessage(resourceManager.getString(R.string.error_number_card));
                }
                break;
            case Constants.TYPE_EMPTY:
                notification.showMessage(resourceManager.getString(R.string.choose_payment_type));
                break;

        }
    }

    private void showDialog(String message) {
        new CustomDialog.DialogBuilder()
                .from(getContext())
                .title(resourceManager.getString(R.string.info_order))
                .message(message)
                .positiveButton(resourceManager.getString(R.string.ok_action), () -> {
                    presenter.detach();
                }, true)
                .create()
                .show(getChildFragmentManager(), Constants.DIALOG_KEY);
    }

    public Observable<Boolean> observeStreetValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        streetEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                validationRelay.accept(!streetEditText.getText().toString().trim().isEmpty());
            }
        });
        return validationRelay;
    }

    public Observable<Boolean> observeHouseValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        houseEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                validationRelay.accept(!houseEditText.getText().toString().trim().isEmpty());
            }
        });
        return validationRelay;
    }

    public Observable<Boolean> observeApartmentValid() {
        final BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
        validationRelay.accept(false);
        apartamentEditText.addTextChangedListener(new SimpleTextChangedListener() {
            @Override
            public void afterTextChanged(final Editable s) {
                validationRelay.accept(!apartamentEditText.getText().toString().trim().isEmpty());
            }
        });
        return validationRelay;
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getContext(), dateListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(getContext(), timeListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        selectDateTime = DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    public void showPersonalInformation(String name, String phone) {
        nameTextView.setText(name);
        phoneTextView.setText(phone);
    }

    @Override
    public void showMessage(String message) {
        showDialog(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }
}
