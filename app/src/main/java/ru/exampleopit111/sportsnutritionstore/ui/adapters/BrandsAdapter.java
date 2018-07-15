package ru.exampleopit111.sportsnutritionstore.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;

/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder> {

    private List<Brand> brands;
    private BrandsListener listener;
    private RequestManager requestManager;

    public BrandsAdapter(List<Brand> brands, BrandsListener listener, RequestManager requestManager) {
        this.brands = brands;
        this.listener = listener;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.bindTo(brand);
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.brandImageView)
        ImageView brandImageView;

        private Brand brand;

        public ViewHolder(View itemView, BrandsListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::launchBrand);
        }

        private void launchBrand(View v) {
            listener.onBrandClick(brand);
        }

        public void bindTo(Brand brand) {
            this.brand = brand;
            requestManager.load(brand.getUrl())
                    //.apply(RequestOptions.centerCropTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_image_empty).centerCrop())
                    // .animate(R.anim.show_view)
                    .into(brandImageView);
        }
    }

    public interface BrandsListener {
        void onBrandClick(Brand brand);
    }
}