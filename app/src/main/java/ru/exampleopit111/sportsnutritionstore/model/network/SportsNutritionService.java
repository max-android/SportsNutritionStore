package ru.exampleopit111.sportsnutritionstore.model.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class SportsNutritionService {

    //В данном классе содержится информация и данные, не подлежащие разглашению,
    // поэтому они были удалены при публикации на Github

    //Часть данных также было заменено на искусственные в целях сохранения коммерческой тайны

    //classified information


    public Single<List<ProductCategory>> categories() {
        return Single.fromCallable(() -> getCategories());
    }

    public Single<List<Product>> products() {
        return Single.fromCallable(() -> getProducts());
    }

    public Single<List<MapEntity>> locationStores() {
        return Single.fromCallable(() -> getStoreAddresses());
    }

    public Single<List<Brand>> brands() {
        return Single.fromCallable(() -> getProducers());
    }

    public List<ProductCategory> getCategories() {
        List<ProductCategory> categories = new ArrayList<>();
        categories.add(new ProductCategory("Аминокислоты"));
        categories.add(new ProductCategory("Анаболические комплексы"));
        categories.add(new ProductCategory("Витамины и минералы"));
        categories.add(new ProductCategory("Гейнеры"));
        categories.add(new ProductCategory("Для суставов и связок"));
        categories.add(new ProductCategory("Омега жиры"));
        categories.add(new ProductCategory("Протеины"));
        categories.add(new ProductCategory("Тестобустеры"));
        categories.add(new ProductCategory("Энергетики"));
        return categories;
    }

    public List<MapEntity> getStoreAddresses() {
        List<MapEntity> stores = new ArrayList<>();
        stores.add(new MapEntity("address1", 55.155773, 37.117761));
        stores.add(new MapEntity("address2", 55.355123, 37.317234));
        stores.add(new MapEntity("address3", 55.755000, 37.717111));
        return stores;
    }

    public List<Brand> getProducers() {
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand("Weider", "classified information"));
        brands.add(new Brand("Omega Universal Nutrition", "classified information"));
        brands.add(new Brand("Maxler", "classified information"));
        brands.add(new Brand("Ultimate Nutrition", "classified information"));
        brands.add(new Brand("Dymatize", "classified information"));
        brands.add(new Brand("Olimp Sport Nutrition", "classified information"));
        brands.add(new Brand("BSN", "classified information"));
        brands.add(new Brand("Optimum Nutrition", "classified information"));
        brands.add(new Brand("SAN", "classified information"));
        brands.add(new Brand("Scitec Nutrition", "classified information"));
        return brands;
    }



    //classified information
    public List<Product> getProducts() {

        List<Product> products = new ArrayList<>();




        products.add(new Product("Amino-x BSN 400 г", "Аминокислоты", "BSN", "Содержание питательных веществ в порции (размер порции: 1 мерная ложка = 14,5 г):\n" +
                "\n" +
                "Всего углеводов < 1 г;\n" +
                "Витамин D (как холекальциферол) – 500 МЕ (125%);\n" +
                "Натрий – 160 мг.\n" +
                "Анаболическая смесь аминокислот 10 гр.:\n" +
                "\n" +
                "L-лейцин (микронизированный);\n" +
                "L-валин (микронизированный);\n" +
                "L-изолейцин (микронизированный);\n" +
                "Таурин (микронизированный);\n" +
                "L-Цитруллин (микронизированный).\n" +
                "Efforsorb EnDura Composite – 2 гр.:\n" +
                "\n" +
                "лимонная кислота;\n" +
                "яблочная кислота;\n" +
                "бикарбонат натрия;\n" +
                "холекальциферол.\n" +
                "Ингредиенты:\n" +
                "\n" +
                "натуральные и искусственные ароматизаторы;\n" +
                "диоксид кремния;\n" +
                "лецитин;\n" +
                "сукралоза;\n" +
                "ацесульфам калия;\n" +
                "краситель FDC.", "смешайте 1 мерную ложку с 180 мл холодной воды или любого напитка на ваш выбор. Принимайте до, во время или после тренировки. Выпивайте сразу после смешивания с водой. Принимайте как минимум за 20-30 минут до приема пищи или шейка.", "Индивидуальная непереносимость компонентов продукта", 10, 1400, "address1", "classified information", false, false, 1));


        return products;
    }
}
