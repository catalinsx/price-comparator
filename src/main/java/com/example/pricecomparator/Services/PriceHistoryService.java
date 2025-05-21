package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceHistoryService {
    private final Utils utils;

    public PriceHistoryService(Utils utils){
        this.utils = utils;
    }

    private List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();

        String[] productFiles = {
                "kaufland_2025-05-01.csv",
                "kaufland_2025-05-08.csv",
                "lidl_2025-05-01.csv",
                "lidl_2025-05-08.csv",
                "profi_2025-05-01.csv",
                "profi_2025-05-08.csv",
                "kaufland_2025-05-15.csv",
                "lidl_2025-05-15.csv",
                "profi_2025-05-15.csv",
        };

        for (String file : productFiles) {
            List<Product> productsFromFile = utils.readProducts(file);
            allProducts.addAll(productsFromFile);
        }

        return allProducts;
    }

    public Map<String, Object> getPriceHistory(String productId, String storeFilter){
       Map<String, Object> result = new LinkedHashMap<>();
       List<Product> allProducts = getAllProducts();

       // adding to a list only the matching products, it verifies the ID for the requested product
        // after that if we have the filter for the store, we check that too
       List<Product> matchingProducts = new ArrayList<>();
       for(Product product : allProducts){
           if(product.getProduct_id().equals(productId)){
               if(storeFilter == null || product.getStore().equalsIgnoreCase(storeFilter)){
                   matchingProducts.add(product);
               }
           }
       }

       if(matchingProducts.isEmpty()){
           result.put("error", "Product not found");
           return result;
       }

       /*
       we are taking the first product found to add the required information
       ex:
       "productId" : "P001"
       "productName" : "lapte zuzu"
        */
       Product firstProduct = matchingProducts.get(0);
       result.put("productId", productId);
       result.put("productName", firstProduct.getProduct_name());

       Set<String> stores = new HashSet<>();
       Map<String, Map<String, Object>> pricesByDate = new LinkedHashMap<>();



        //foreach product in matchingProducts
        //-> takes the storeName, date and price and besides that we add each store to our HashSet

       for(Product product : matchingProducts){
           String date = String.valueOf(product.getDate());
           String store = product.getStore();
           double price = product.getPrice();

           stores.add(store);

           // it checks if the date mentioned exists, if not we are creating a map like this
           /*
           "2025-05-01" : {
               "lidl" : 9.99
               "profi" : 12.2
           }
            */

           if(!pricesByDate.containsKey(date)){
               pricesByDate.put(date, new HashMap<>());
           }

           pricesByDate.get(date).put(store, price);

       }

       result.put("stores", new ArrayList<>(stores));
       result.put("priceHistory", pricesByDate);

       return result;

    }

}
