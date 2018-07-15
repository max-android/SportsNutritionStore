package ru.exampleopit111.sportsnutritionstore.presentation.presenter.method_courier;

import javax.inject.Inject;

import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.domain.method_courier.MethodCourierInteractor;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.PositionTabObj;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.OrderRequestEntity;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.base.BasePresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.method_courier.MethodCourierView;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Screens;
import ru.terrakok.cicerone.Router;

/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class MethodCourierPresenter extends BasePresenter<MethodCourierView> {

    @Inject
    MethodCourierInteractor interactor;
    @Inject
    ResourceManager resourceManager;
    @Inject
    Router router;

    public MethodCourierPresenter(MethodCourierInteractor interactor, ResourceManager resourceManager, Router router) {
        this.interactor = interactor;
        this.resourceManager = resourceManager;
        this.router = router;
    }

    public void attach() {
        String name = interactor.user()
                .getPreferences()
                .getString(Constants.USER_NAME_HOLDER, Constants.DEFAULT_NAME);
        String phone = interactor.user()
                .getPreferences()
                .getString(Constants.KEY_PHONE_HOLDER, Constants.DEFAULT_PHONE);

        view.showPersonalInformation(name, phone);
    }

    public void sendOrderRequest(OrderRequestEntity orderRequestEntity) {
        switch (orderRequestEntity.getType_payment()) {
            case Constants.TYPE_INCASH:

                //здесь будет отправка запроса на бронирование товара в магазине
                //interactor.sendOrderRequest
                view.showMessage(resourceManager.getString(R.string.message_about_order));
                break;
            case Constants.TYPE_CARD:

                //здесь будет отправка запроса на бронирование товара в магазине и оплату по карте
                //interactor.sendOrderRequest
                view.showMessage(resourceManager.getString(R.string.message_about_order_by_card));
                break;
        }
    }

    public void detach() {
        router.replaceScreen(Screens.MAIN_FRAGMENT, new PositionTabObj(Constants.MAIN_TAB_POSITION));
    }
}
