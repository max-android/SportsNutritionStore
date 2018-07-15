package ru.exampleopit111.sportsnutritionstore.ui.sports_nutrition_product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.App;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;
import ru.exampleopit111.sportsnutritionstore.presentation.presenter.product_description.ProductDescriptionPresenter;
import ru.exampleopit111.sportsnutritionstore.presentation.view.product_description.ProductDescriptionView;
import ru.exampleopit111.sportsnutritionstore.ui.base.BaseFragment;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.Notification;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class ProductDescriptionFragment extends BaseFragment implements ProductDescriptionView {

    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.priceTextView)
    TextView priceTextView;
    @BindView(R.id.brandTextView)
    TextView brandTextView;
    @BindView(R.id.productImageView)
    ImageView productImageView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.applaingTextView)
    TextView applaingTextView;
    @BindView(R.id.contrainTextView)
    TextView contrainTextView;
    @BindView(R.id.gotoBasketButton)
    Button gotoBasketButton;

    @Inject
    ProductDescriptionPresenter presenter;
    @Inject
    ResourceManager resourceManager;
    @Inject
    RequestManager requestManager;

    private Notification notification;
    private Product product;

    public ProductDescriptionFragment() {
    }

    public static ProductDescriptionFragment newInstance(Product product) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.PRODUCT_KEY, product);
        fragment.setArguments(args);
        return fragment;
    }

    private Product getProduct() {
        return (Product) getArguments().getSerializable(Constants.PRODUCT_KEY);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product_description;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().injectProductDescriptionFragment(this);
        notification = new Notification(view, getContext());
        presenter.bindView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDescriptionProduct();
    }

    private void initDescriptionProduct() {
        if (getProduct() != null) {
            product = getProduct();
            nameTextView.setText(product.getName());
            priceTextView.setText(String.format("%d%s", product.getPrice(),
                    resourceManager.getString(R.string.unit_of_price)));
            brandTextView.setText(product.getProducer());
            requestManager.load(product.getImage())
                    // .apply(RequestOptions.centerCropTransform())
                    .apply(new RequestOptions()
                            .placeholder(resourceManager.getDrawable(R.drawable.ic_image_empty))
                            .centerCrop())
                    // .placeholder(R.drawable.ic_image_empty)
                    // .animate(R.anim.show_view)
                    .into(productImageView);
            animateImage();
            descriptionTextView.setText(product.getDescription());
            applaingTextView.setText(product.getApplying());
            contrainTextView.setText(product.getContraindications());
            if (product.isBasket()) {
                updateStatusButton();
            }
        }
        gotoBasketButton.setOnClickListener(v -> presenter.onBusketButtonClick(product));
    }

    private void updateStatusButton() {
        gotoBasketButton.setEnabled(false);
        gotoBasketButton.setText(resourceManager.getString(R.string.product_already_basket));
    }

    private void animateImage() {
        Animation animation = AnimationUtils.loadAnimation(resourceManager.getContext(),
                R.anim.show_view);
        productImageView.startAnimation(animation);
    }

    @Override
    public void showMessage(String message) {
        notification.showMessage(message);
        updateStatusButton();
    }

    @Override
    public void showError(String error) {
        notification.showMessage(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeResources();
        presenter.unbindView();
    }
}
