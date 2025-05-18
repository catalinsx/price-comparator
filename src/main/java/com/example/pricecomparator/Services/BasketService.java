package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class BasketService {
    private final Utils utils;

    public BasketService(Utils utils) {
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

    public Map<String, Object> optimizeBasket(List<String> productIds){
        Map<String, Object> result = new HashMap<>();

        if(productIds.isEmpty()){
            result.put("error", "The basket is empty");
            return result;
        }

        List<Product> allProducts = getAllProducts();

        Set<String> stores = new HashSet<>();
        for(Product product : allProducts){
            stores.add(product.getStore());
        }

        String bestStore = null; // the store with lowest total price
        double bestStoreTotal = Double.MAX_VALUE; // lowest total price found

        for(String store: stores){
            double total = 0;
            boolean canBuyAllProducts = true;

            // 1. check each store to find the best one for buying everything
           for(String productId : productIds){
               Product foundProduct = null;
               for(Product product : allProducts){
                   if(product.getProduct_id().equals(productId) && product.getStore().equals(store)){
                       if(foundProduct == null || product.getPrice() < foundProduct.getPrice()){
                           foundProduct = product;
                       }
                   }
               }

               // if product no available at this store, can't buy everything here
               if(foundProduct == null){
                   canBuyAllProducts = false;
                   break;
               }
               total = total + foundProduct.getPrice();
           }

           // if this store has all products / total price is better than previous best, this is our new store/total
           if(canBuyAllProducts && total < bestStoreTotal){
               bestStore = store;
               bestStoreTotal = total;
           }
        }

        // 2. splitBasketOption, buying each product at its cheapest store
        Map<String, List<Product>> splitbasket = new HashMap<>();
        double splitTotal = 0;
        List<String> foundProductIds = new ArrayList<>();

        for(String productId : productIds){
            Product cheapestProduct = null;

            for(Product product : allProducts){
                if(product.getProduct_id().equals(productId)){
                    if(cheapestProduct == null || product.getPrice() < cheapestProduct.getPrice()){
                        cheapestProduct = product;
                    }
                }
            }

            if(cheapestProduct != null){
                String store = cheapestProduct.getStore();

                if(!splitbasket.containsKey(store)){
                    splitbasket.put(store, new ArrayList<>());
                }

                splitbasket.get(store).add(cheapestProduct);
                splitTotal = splitTotal + cheapestProduct.getPrice();
                foundProductIds.add(productId);
            }
        }

        // 3. formatting the split for the response
        Map<String, List<Map<String, Object>>> splitBaskedFormatted = new HashMap<>();

        // for each store in our split basket
        for(Map.Entry<String, List<Product>> entry : splitbasket.entrySet()){
            String store = entry.getKey(); // current store
            List<Product> products = entry.getValue(); // products to buy at this store
            List<Map<String, Object>> storeProducts = new ArrayList<>(); // formatted product list

            for(Product product : products){
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("productId", product.getProduct_id());
                productInfo.put("productName", product.getProduct_name());
                productInfo.put("price", product.getPrice());

                storeProducts.add(productInfo);
            }

            // add the formatted products for this store
            splitBaskedFormatted.put(store, storeProducts);
        }

        // 4. building the recommendation
        if(bestStore != null){
            result.put("bestStore", bestStore);
            result.put("bestStoreTotal", formatPrice(bestStoreTotal) + " RON");

            // check if split basket is cheaper than single store
            if(splitTotal < bestStoreTotal){
                double savings = Double.parseDouble(formatPrice(bestStoreTotal - splitTotal));
                result.put("recommendation", "You save " + savings + " RON if you split the basket");
                result.put("splitTotal", formatPrice(splitTotal) + " RON");
                result.put("splitBasket", splitBaskedFormatted);
                result.put("bestOption", "split");
            } else {
                result.put("recommendation", "It is more convenient to buy everything from " + bestStore);
                result.put("bestOption", "single");
            }
        } else {
            // no single store has all products
            if (foundProductIds.size() == productIds.size()) {
                result.put("recommendation", "You have to split the products between different stores");
            } else {
                // some products don't exist in any store
                result.put("recommendation", "Some products are not available in any store");
            }

            result.put("splitTotal", formatPrice(splitTotal) + " RON");
            result.put("splitBasket", splitBaskedFormatted);
            result.put("bestOption", "split");
        }

        return  result;
    }

    private String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(price);
    }

}

