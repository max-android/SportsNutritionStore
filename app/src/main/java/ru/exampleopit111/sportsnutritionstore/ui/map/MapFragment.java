package ru.exampleopit111.sportsnutritionstore.ui.map;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.map.MapPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.map.MapView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.LatLngUtils;
import ru.exampleopit111.sportsnutritionstore.ui.common.LocationPermission;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;

/**
 * Created Максим on 10.06.2018.
 * Copyright © Max
 */
public class MapFragment extends BaseFragment implements MapView, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.mapProgressBar)
    ProgressBar mapProgressBar;
    @BindView(R.id.routeFAB)
    FloatingActionButton routeFAB;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @Inject
    MapPresenter presenter;
    @Inject
    ResourceManager resourceManager;
    @Inject
    LocationPermission locationPermission;

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private Location clientLocations;
    private List<LatLng> latLngRouteList;
    private Notification notification;
    private SupportMapFragment mapFragment;
    private BottomSheetBehavior bottomSheetBehavior;
    private Spinner spinner;
    private LatLng selectStoreForRoute;
    private LatLng clientLatLngForRoute;
    private ArrayAdapter<String> addressAdapter;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        notification = new Notification(view, getContext());
        latLngRouteList = new ArrayList<>();
        App.getAppComponent().injectMapFragmentFragment(this);
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.attach();
        initMap();
        requestPermission();
        setFabListener();
        initBottomSheet();
        initAddressAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
    }

    private void requestPermission() {
        locationPermission.requestPermission(getContext(), this::buildGoogleApiClient);
    }

    private void setFabListener() {
        routeFAB.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED));
    }

    private void initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        // настройка состояний нижнего экрана
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        // настройка максимальной высоты
        int bottomSheetBehaviorHeight = (getResources().getDisplayMetrics().heightPixels / 100) * 21;
        bottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
        // настройка возможности скрыть элемент при свайпе вниз
        bottomSheetBehavior.setHideable(true);
        // настройка колбэков при изменениях
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    routeFAB.animate().scaleX(1).scaleY(1).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    routeFAB.animate().scaleX(0).scaleY(0).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        spinner = bottomSheet.findViewById(R.id.storesSpinner);

        Button routerButton = bottomSheet.findViewById(R.id.routerButton);

        routerButton.setOnClickListener(v -> {
                    if (clientLatLngForRoute != null) {
                        presenter.initRouter(LatLngUtils.transform(clientLatLngForRoute),
                                LatLngUtils.transform(new LatLng(selectStoreForRoute.latitude,
                                        selectStoreForRoute.longitude)),
                                true, "ru",
                                resourceManager.getString(R.string.key2));

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    } else {
                        notification.showMessage(resourceManager.getString(R.string.turn_on_location));
                    }
                }
        );
    }

    private void initAddressAdapter() {
        addressAdapter = new ArrayAdapter<>(getContext(), R.layout.array_adapter_layout,
                R.id.arrayTextView);
        spinner.setAdapter(addressAdapter);
        spinner.setOnItemSelectedListener(addressListener);
    }

    private final AdapterView.OnItemSelectedListener addressListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectStoreForRoute = latLngRouteList.get(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    private void showLocationStores(List<MapEntity> stores) {
        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            for (MapEntity mapEntity : stores) {
                addressAdapter.add(mapEntity.getAddress());
                LatLng location = new LatLng(mapEntity.getLatitude(), mapEntity.getLongitude());
                setupPositionStore(location, googleMap, mapEntity);
            }
        });
    }

    private void setupPositionStore(LatLng location, GoogleMap googleMap, MapEntity mapEntity) {
        latLngRouteList.add(location);
        MarkerOptions option = new MarkerOptions();
        option.title(resourceManager.getString(R.string.address_snippet));
        option.snippet(mapEntity.getAddress());
        option.position(location);
        option.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.body_profile_50));
        googleMap.addMarker(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constants.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    notification.showMessage(resourceManager.getString(R.string.permission_location_granted));
                } else {
                    notification.showMessage(resourceManager.getString(R.string.permission_location_denied));
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private synchronized void buildGoogleApiClient() {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                            notification.showMessage(resourceManager.getString(R.string.connection_paused));
                            if (googleApiClient != null) {
                                googleApiClient.connect();
                            }
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {
                            Location lastLocation
                                    = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                            if (lastLocation != null) {
                                clientLocations = lastLocation;
                                receiveLocation();
                            } else {
                                notification.showMessage(resourceManager.getString(R.string.turn_on_location));
                            }
                        }
                    }).addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @SuppressLint("MissingPermission")
    private void receiveLocation() {
        if (clientLocations != null) {
            setupPositionClient(new LatLng(clientLocations.getLatitude(), clientLocations.getLongitude()));
            zoomRoute();
            drawPath();
        } else {
            notification.showMessage(resourceManager.getString(R.string.turn_on_location));
        }
    }

    private void setupPositionClient(LatLng location) {
        latLngRouteList.add(location);
        clientLatLngForRoute = location;
        MarkerOptions option = new MarkerOptions();
        option.title(resourceManager.getString(R.string.map_hint));
        option.snippet(resourceManager.getString(R.string.my_location));
        option.position(location);
        option.icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_client_positions));
        googleMap.addMarker(option);
    }

    public void zoomRoute() {
        if (googleMap == null || latLngRouteList.isEmpty()) return;
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : latLngRouteList) {
            boundsBuilder.include(latLngPoint);
        }
        int routePadding = 10;
        LatLngBounds latLngBounds = boundsBuilder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
    }

    private void drawPath() {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (LatLng latLngPoint : latLngRouteList) {
            polylineOptions.add(latLngPoint);
        }
        polylineOptions.color(resourceManager.getColor(R.color.black));
        polylineOptions.width(6);
        polylineOptions.geodesic(false);
        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void showStores(List<MapEntity> stores) {
        showLocationStores(stores);
    }

    @Override
    public void showRoute(List<LatLng> locations) {
        createRoute(locations);
    }

    private void createRoute(List<LatLng> locations) {
        PolylineOptions line = new PolylineOptions();
        line.width(6).color(resourceManager.getColor(R.color.red));
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < locations.size(); i++) {
            line.add(locations.get(i));
            latLngBuilder.include(locations.get(i));
        }
        googleMap.addPolyline(line);
        int size = getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
        googleMap.moveCamera(track);
    }

    @Override
    public void showProgress() {
        mapProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        mapProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    private void stopFusedLocation() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopFusedLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopFusedLocation();
        presenter.closeResources();
        presenter.unbindView();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        notification.showMessage(resourceManager.getString(R.string.connection_failed));
    }
}
