package ru.exampleopit111.sportsnutritionstore.ui.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;

/**
 * Created Максим on 25.06.2018.
 * Copyright © Max
 */
public class Filter {

    public Filter() {
    }

    public static List<Product> filter(String filter, List<Product> filterProducts) {
        switch (filter) {
            case TypeFilter.DEFAULT:
                break;
            case TypeFilter.ALPHABETICALLY:
                Collections.sort(filterProducts, AlphabeticallyComparator);
                break;
            case TypeFilter.DESCENDING:
                Collections.sort(filterProducts, DescendingComparator);
                break;
            case TypeFilter.ASCENDING:
                Collections.sort(filterProducts, AscendingComparator);
                break;
            case TypeFilter.BREND:
                Collections.sort(filterProducts, BrendComparator);
                break;
        }
        return filterProducts;
    }

    private static Comparator<Product> AlphabeticallyComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product1, Product product2) {
            return product1.getName().compareTo(product2.getName());
        }
    };

    private static Comparator<Product> BrendComparator = new Comparator<Product>() {
        @Override
        public int compare(Product product1, Product product2) {
            return product1.getProducer().compareTo(product2.getProducer());
        }
    };

    private static Comparator<Product> AscendingComparator = new Comparator<Product>() {

        @Override
        public int compare(Product product1, Product product2) {
            return product1.getPrice() - product2.getPrice();
        }
    };

    private static Comparator<Product> DescendingComparator = new Comparator<Product>() {

        @Override
        public int compare(Product product1, Product product2) {
            return product2.getPrice() - product1.getPrice();
        }
    };
}
