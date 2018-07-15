package ru.exampleopit111.sportsnutritionstore.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<ProductCategory> categories;
    private CategoryListener listener;
    private View view;

    public CategoryAdapter(List<ProductCategory> categories, CategoryListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategory category = categories.get(position);
        holder.bindTo(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void adOnMove(int fromPos, int toPos) {
        Collections.swap(categories, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    public void adOnSwiped(RecyclerView.ViewHolder viewHolder) {
        int swipedPosition = viewHolder.getAdapterPosition();
        categories.remove(swipedPosition);
        notifyItemRemoved(swipedPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryTextView)
        TextView categoryTextView;
        private ProductCategory category;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::launchCategory);
        }

        private void launchCategory(View v) {
            listener.onCategoryClick(category);
        }

        public void bindTo(ProductCategory category) {
            this.category = category;
            categoryTextView.setText(category.getCategory());
        }
    }

    public interface CategoryListener {
        void onCategoryClick(ProductCategory category);
    }
}
