package ru.exampleopit111.sportsnutritionstore.ui.self_delivery_method;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.OrderRequestEntity;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.self_delivery_method.SelfDeliveryMethodPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.self_delivery_method.SelfDeliveryMethodView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;
import ru.exampleopit111.sportsnutritionstore.ui.custom.CustomDialog;
import ru.exampleopit111.sportsnutritionstore.ui.custom.FormEditText;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class SelfDeliveryMethodFragment extends BaseFragment implements SelfDeliveryMethodView {

    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
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
    @BindView(R.id.selfDeliveryLinearLayout)
    LinearLayout selfDeliveryLinearLayout;

    private Notification notification;
    private String payment_type;
    private int count_store;
    private double summ_latitude;
    private double summ_longitude;

    @Inject
    SelfDeliveryMethodPresenter presenter;

    @Inject
    ResourceManager resourceManager;

    public SelfDeliveryMethodFragment() {
    }

    public static SelfDeliveryMethodFragment newInstance(BasketEntity basketEntity) {
        SelfDeliveryMethodFragment fragment = new SelfDeliveryMethodFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BASKET_KEY, basketEntity);
        fragment.setArguments(args);
        return fragment;
    }

    private BasketEntity getSelfDeliveryEntity() {
        return (BasketEntity) getArguments().getSerializable(Constants.BASKET_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_self_delivery_method;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectSelfDeliveryMethodFragment(this);
        notification = new Notification(view, getContext());
        presenter.bindView(this);
        presenter.attach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap();
        initComponents();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this::showStoreOnMap);
    }

    private void showStoreOnMap(GoogleMap googleMap) {

        for (MapEntity mapEntity : getListEntity()) {
            createCoordForScale(mapEntity);
            LatLng location = new LatLng(mapEntity.getLatitude(), mapEntity.getLongitude());
            setupPositionStore(location, googleMap, mapEntity);
        }
        scaling(googleMap);
    }

    private void createCoordForScale(MapEntity mapEntity) {
        count_store++;
        summ_latitude = summ_latitude + mapEntity.getLatitude();
        summ_longitude = summ_longitude + mapEntity.getLongitude();
    }

    private void scaling(GoogleMap googleMap) {
        LatLng scale = new LatLng(summ_latitude / count_store, summ_longitude / count_store);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(scale, 8));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(scale)             // Sets the center of the map to location user
                .zoom(8)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setupPositionStore(LatLng location, GoogleMap googleMap, MapEntity mapEntity) {
        MarkerOptions option = new MarkerOptions();
        option.title(resourceManager.getString(R.string.address_snippet));
        option.snippet(mapEntity.getAddress());
        option.position(location);
        option.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.body_profile_50));
        googleMap.addMarker(option);
    }


    private HashSet<String> getListAddress() {
        BasketEntity basketEntity = getSelfDeliveryEntity();
        basketEntity.getProducts();
        HashSet<String> address = new HashSet<>();
        for (Product product : basketEntity.getProducts()) {
            address.add(product.getAddress());
        }
        return address;
    }

    private HashSet<MapEntity> getListEntity() {

        HashSet<MapEntity> entityHashSet = new HashSet<>();

        for (String address : getListAddress()) {
            if (address.equals(resourceManager.getString(R.string.address1))) {
                entityHashSet.add(new MapEntity(address, 55.155773, 37.117761));
            }

            if (address.equals(resourceManager.getString(R.string.address3))) {
                entityHashSet.add(new MapEntity(address, 55.355123, 37.317234));
            }

            if (address.equals(resourceManager.getString(R.string.address2))) {
                entityHashSet.add(new MapEntity(address, 55.755000, 37.717111));
            }
        }
        return entityHashSet;
    }

    @SuppressLint("CheckResult")
    private void initComponents() {
        cardLinearLayout.setLayoutTransition(new LayoutTransition());
        selfDeliveryLinearLayout.setLayoutTransition(new LayoutTransition());
        totalCoastTextView.setText(getSelfDeliveryEntity().getBasketPrice());

        inCashCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cardCheckBox.setChecked(false);
                cardLinearLayout.setVisibility(View.GONE);
                payment_type = Constants.TYPE_INCASH;
                checkoutButton.setEnabled(true);
            } else {
                checkoutButton.setEnabled(false);
            }

        });

        cardCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                inCashCheckBox.setChecked(false);
                cardLinearLayout.setVisibility(View.VISIBLE);
                payment_type = Constants.TYPE_CARD;


                numberCardEditText.observeValid().subscribe(value -> {
                    checkoutButton.setEnabled(value);
                });
            } else {
                checkoutButton.setEnabled(false);
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
                        totalCoastTextView.getText().toString()
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
                            numberCardEditText.getText().toString()

                    ));
                } else {
                    notification.showMessage(resourceManager.getString(R.string.error_number_card));
                }
                break;
        }
    }

    private void showDialog(String message) {
        CustomDialog dialog = new CustomDialog.DialogBuilder()
                .from(getContext())
                .title(resourceManager.getString(R.string.info_order))
                .message(message)
                .positiveButton(resourceManager.getString(R.string.ok_action), () -> {
                    presenter.detach();
                }, true)
                .create()
                .show(getChildFragmentManager(), Constants.DIALOG_KEY);
    }

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
