package ru.exampleopit111.sportsnutritionstore.model.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.model.network.SportsNutritionService;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class SportsNutritionRepository {

    @Inject
    SportsNutritionService service;

    public SportsNutritionRepository(SportsNutritionService service) {
        this.service = service;
    }

    public Single<List<Product>> products() {
        return service.products();
    }

    public Single<List<ProductCategory>> categories() {
        return service.categories();
    }

    public Single<List<MapEntity>> locationStores() {
        return service.locationStores();
    }

    public Single<List<Brand>> brands() {
        return service.brands();
    }
}
