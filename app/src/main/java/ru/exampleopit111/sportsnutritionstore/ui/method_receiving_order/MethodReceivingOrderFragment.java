package ru.exampleopit111.sportsnutritionstore.ui.method_receiving_order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_receiving_order.MethodReceivingOrderPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.method_receiving_order.MethodReceivingOrderView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class MethodReceivingOrderFragment extends BaseFragment implements MethodReceivingOrderView {

    @BindView(R.id.courierImageView)
    ImageView courierImageView;
    @BindView(R.id.shopImageView)
    ImageView shopImageView;
    @BindView(R.id.courierPriceTextView)
    TextView courierPriceTextView;
    @BindView(R.id.shopPriceTextView)
    TextView shopPriceTextView;

    @Inject
    ResourceManager resourceManager;
    @Inject MethodReceivingOrderPresenter presenter;

    public MethodReceivingOrderFragment() {
    }

    public static MethodReceivingOrderFragment newInstance(BasketEntity basketEntity) {
        MethodReceivingOrderFragment fragment = new MethodReceivingOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.BASKET_KEY, basketEntity);
        fragment.setArguments(args);
        return fragment;
    }

    private BasketEntity getBasketEntity() {
        return (BasketEntity) getArguments().getSerializable(Constants.BASKET_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_method_receiving_order;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectMethodReceivingOrderFragment(this);
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        selectMethodReceivingOrder();
    }

    private void initComponents() {
        shopPriceTextView.setText(getBasketEntity().getBasketPrice());
        courierPriceTextView.setText(String.format("%d%s",
                calculateShippingPrice(getBasketEntity().getBasketPrice()),
                resourceManager.getString(R.string.unit_of_price)));
        getBasketEntity().setBasketPrice(courierPriceTextView.getText().toString());
    }

    private void selectMethodReceivingOrder(){
        courierImageView.setOnClickListener(v -> presenter.onClickCourierImageView(getBasketEntity()));
        shopImageView.setOnClickListener(v -> presenter.onClickShopImageView(getBasketEntity()));
    }

    private int calculateShippingPrice(String price) {
        int price_basket = Integer.parseInt(
                price.replace(resourceManager.getString(R.string.unit_of_price),
                        resourceManager.getString(R.string.empty)));

        if (price_basket < Constants.BASKET_PRICE) {
            price_basket = price_basket + Constants.FIRST_RATE;
        } else {
            price_basket = price_basket + Constants.SECOND_RATE;
        }
        return price_basket;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }
}
