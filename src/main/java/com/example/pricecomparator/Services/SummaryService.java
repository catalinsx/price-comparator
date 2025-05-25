package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.Discount;
import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SummaryService {
    private final Utils utils;

    public SummaryService(Utils utils){
        this.utils = utils;
    }

    private List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();

        String[] productFiles = {
                "kaufland_2025-05-01.csv",
                "lidl_2025-05-01.csv",
                "profi_2025-05-01.csv",
        };

        for (String file : productFiles) {
            List<Product> productsFromFile = utils.readProducts(file);
            allProducts.addAll(productsFromFile);
        }

        return allProducts;
    }

    private List<Discount> getAllDiscounts(){
        List<Discount> allDiscounts = new ArrayList<>();

        String[] discountFiles = {
                "lidl_discounts_2025-05-01.csv",
                "kaufland_discounts_2025-05-01.csv",
                "profi_discounts_2025-05-01.csv",
        };

        for(String file : discountFiles){
            List<Discount> discountFile= utils.readDiscounts(file);
            allDiscounts.addAll(discountFile);
        }
        return allDiscounts;

    }

    public Map<String, Object> getSummary(){
        List<Product> allProducts = getAllProducts();
        List<Discount> allDiscounts = getAllDiscounts();
        Set<String> stores = new HashSet<>();
        int productCount = 0;

        for(Product product : allProducts){
            productCount++;
            stores.add(product.getStore());
        }

        int discountCount = allDiscounts.size();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalProducts", productCount);
        summary.put("availableStores", stores);
        summary.put("totalDiscounts", discountCount);

        return summary;
    }

}
