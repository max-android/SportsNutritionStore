package ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_receiving_order;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.model.entities.common.BasketEntity;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.method_receiving_order.MethodReceivingOrderView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class MethodReceivingOrderPresenter extends BasePresenter<MethodReceivingOrderView> {

    @Inject
    Router router;

    public MethodReceivingOrderPresenter(Router router) {
        this.router = router;
    }

    public void onClickCourierImageView(BasketEntity basketEntity) {
        router.replaceScreen(Screens.METHOD_COURIER_FRAGMENT, basketEntity);
    }

    public void onClickShopImageView(BasketEntity basketEntity) {
        router.replaceScreen(Screens.SELF_DELIVERY_FRAGMENT, basketEntity);
    }
}
