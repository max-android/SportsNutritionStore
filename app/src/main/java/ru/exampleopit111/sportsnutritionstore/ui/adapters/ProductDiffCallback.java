package ru.exampleopit111.sportsnutritionstore.ui.adapters;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created by Максим on 01.11.2017.
 */

public class ProductDiffCallback extends DiffUtil.Callback {
    private List<Product> oldProduct;
    private List<Product> newProduct;

    public ProductDiffCallback(List<Product> oldCharacters, List<Product> newCharacters) {
        this.oldProduct = oldCharacters;
        this.newProduct = newCharacters;
    }

    @Override
    public int getOldListSize() {
        return oldProduct.size();
    }

    @Override
    public int getNewListSize() {
        return newProduct.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return equalsData(String.valueOf(oldProduct.get(oldItemPosition).isFavorite()),
                String.valueOf(newProduct.get(newItemPosition).isFavorite()),
                String.valueOf(oldProduct.get(oldItemPosition).isBasket()),
                String.valueOf(newProduct.get(newItemPosition).isBasket())
        );
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProduct.get(oldItemPosition).equals(newProduct.get(newItemPosition));
    }

    private boolean equalsData(String oldFavorite, String newFavorite, String oldBasket, String newBasket) {
        if (oldFavorite.equals(newFavorite) || oldBasket.equals(newBasket)) {
            return true;
        } else {
            return false;
        }
    }

}
