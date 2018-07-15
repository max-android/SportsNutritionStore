package ru.exampleopit111.sportsnutritionstore.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.system.ResourceManager;

/**
 * Created Максим on 01.07.2018.
 * Copyright © Max
 */
public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private BusketItemListener itemListener;
    private RequestManager requestManager;
    private BasketDeleteListener deleteBasketListener;
    private ResourceManager resourceManager;
    private PlusCountSelectProductListener plusCountListener;
    private MinusCountSelectProductListener minusCountListener;

    public BasketAdapter(List<Product> products,
                         BusketItemListener itemListener,
                         RequestManager requestManager,
                         BasketDeleteListener deleteBasketListener,
                         ResourceManager resourceManager,
                         PlusCountSelectProductListener plusCountListener,
                         MinusCountSelectProductListener minusCountListener) {
        //this.products = products;
        this.products.addAll(products);
        this.itemListener = itemListener;
        this.requestManager = requestManager;
        this.deleteBasketListener = deleteBasketListener;
        this.resourceManager = resourceManager;
        this.plusCountListener = plusCountListener;
        this.minusCountListener = minusCountListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        animateImage(holder);
        holder.bindTo(product);
    }

    @Override
    public int getItemCount() {
        return (products != null ? products.size() : 0);
    }

    public void updateData(List<Product> data) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProductDiffCallback(this.products, data));
        products = data;
        diffResult.dispatchUpdatesTo(this);
    }

    private void animateImage(ViewHolder holder) {
        Animation animation = AnimationUtils.loadAnimation(resourceManager.getContext(),
                R.anim.rotate_view);
        holder.productImageView.startAnimation(animation);
    }

    public void adOnMove(int fromPos, int toPos) {
        Collections.swap(products, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    public void adOnSwiped(RecyclerView.ViewHolder viewHolder) {
        int swipedPosition = viewHolder.getAdapterPosition();
        products.remove(swipedPosition);
        notifyItemRemoved(swipedPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImageView)
        ImageView productImageView;
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.priceTextView)
        TextView priceTextView;
        @BindView(R.id.deleteBasketImageView)
        ImageView deleteBasketImageView;
        @BindView(R.id.valueTextView)
        TextView valueTextView;
        @BindView(R.id.minusButton)
        ImageButton minusButton;
        @BindView(R.id.plusButton)
        ImageButton plusButton;
        private int value = 1;
        private int minValue = 0;
        private int maxValue;

        private Product product;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::launchProduct);
            deleteBasketImageView.setOnClickListener(this::notifyDeleteItemBasket);
            plusButton.setOnClickListener(this::notifyPlusCountProduct);
            minusButton.setOnClickListener(this::notifyMinusCountProduct);
        }

        private void launchProduct(View v) {
            itemListener.onBusketItemClick(product);
        }

        private void notifyDeleteItemBasket(View v) {
            deleteBasketListener.deleteItemFromBasketClick(product);
        }

        private void notifyPlusCountProduct(View v) {
            plusCountListener.onPlusClick(product, value + 1);
        }

        private void notifyMinusCountProduct(View v) {
            minusCountListener.onMinusClick(product, value - 1);
        }

        public void bindTo(Product product) {
            this.product = product;
            nameTextView.setText(product.getName());
            priceTextView.setText(String.format("%d%s", product.getPrice(),
                    resourceManager.getString(R.string.unit_of_price)));
            value = product.getCurrentOrder();
            maxValue = product.getAvailability();
            valueTextView.setText(String.valueOf(value));
            minusButton.setVisibility(value == minValue ? View.GONE : View.VISIBLE);
            plusButton.setVisibility(value == maxValue ? View.GONE : View.VISIBLE);
            requestManager.load(product.getImage())
                    //.apply(RequestOptions.centerCropTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_image_empty).centerCrop())
                    // .animate(R.anim.show_view)
                    .into(productImageView);
        }
    }

    public interface BusketItemListener {
        void onBusketItemClick(Product product);
    }

    public interface BasketDeleteListener {
        void deleteItemFromBasketClick(Product product);
    }

    public interface PlusCountSelectProductListener {
        void onPlusClick(Product product, int value);
    }

    public interface MinusCountSelectProductListener {
        void onMinusClick(Product product, int value);
    }
}


