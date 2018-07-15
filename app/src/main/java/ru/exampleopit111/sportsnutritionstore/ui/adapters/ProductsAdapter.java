package ru.exampleopit111.sportsnutritionstore.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private ProductListener listener;
    private RequestManager requestManager;
    private BasketListener basketListener;
    private FavoriteListener favoriteListener;
    private ResourceManager resourceManager;
    private AdapterViewType viewTypeEnum;

    public ProductsAdapter(List<Product> products,
                           ResourceManager resourceManager,
                           ProductListener listener,
                           RequestManager requestManager,
                           BasketListener basketListener,
                           FavoriteListener favoriteListener,
                           AdapterViewType viewTypeEnum
    ) {
        //this.products = products;
        this.products.addAll(products);
        this.resourceManager = resourceManager;
        this.listener = listener;
        this.requestManager = requestManager;
        this.basketListener = basketListener;
        this.favoriteListener = favoriteListener;
        this.viewTypeEnum = viewTypeEnum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewTypeEnum) {
            case LIN_TYPE:
                view = inflater.inflate(R.layout.item_lin_product, parent, false);
                break;
            case GRID_TYPE:
                view = inflater.inflate(R.layout.item_grid_product, parent, false);
                break;
        }
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
        @BindView(R.id.favoriteImageView)
        ImageView favoriteImageView;
        @BindView(R.id.basketImageView)
        ImageView basketImageView;

        private Product product;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::launchProduct);
            basketImageView.setOnClickListener(this::notifyBasket);
            favoriteImageView.setOnClickListener(this::notifyFavorite);
        }

        private void launchProduct(View v) {
            listener.onCategoryClick(product);
        }

        private void notifyBasket(View v) {
            basketListener.onBasketClick(product);
        }

        private void notifyFavorite(View v) {
            favoriteListener.onFavoriteClick(product);
        }

        public void bindTo(Product product) {
            this.product = product;
            nameTextView.setText(product.getName());
            priceTextView.setText(String.format("%d%s", product.getPrice(),
                    resourceManager.getString(R.string.unit_of_price)));

            updateBackgroundImage(product.isFavorite(), product.isBasket());

            requestManager.load(product.getImage())
                    .apply(new RequestOptions()
                            .placeholder(resourceManager.getDrawable(R.drawable.ic_image_empty))
                            .centerCrop())
                    //.placeholder(R.drawable.ic_image_empty)
                    // .animate(R.anim.show_view)
                    .into(productImageView);
        }

        private void updateBackgroundImage(boolean favorite, boolean basket) {
            if (favorite) {
                favoriteImageView.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_favorite));
            } else {
                favoriteImageView.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_favorite_empty));
            }

            if (basket) {
                basketImageView.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_basket));
            } else {
                basketImageView.setImageDrawable(resourceManager.getDrawable(R.drawable.ic_basket_empty));
            }
        }
    }

    public interface ProductListener {
        void onCategoryClick(Product product);
    }

    public interface BasketListener {
        void onBasketClick(Product product);
    }

    public interface FavoriteListener {
        void onFavoriteClick(Product product);
    }
}
