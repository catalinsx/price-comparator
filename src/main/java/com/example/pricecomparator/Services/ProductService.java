package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final Utils utils;

    public ProductService(Utils utils) {
        this.utils = utils;
    }

    private List<Product> getAllProducts(){
        List<Product> allProducts = new ArrayList<>();

        String[] productFiles = {
                "kaufland_2025-05-01.csv",
                "kaufland_2025-05-08.csv",
                "lidl_2025-05-01.csv",
                "lidl_2025-05-08.csv",
                "profi_2025-05-01.csv",
                "profi_2025-05-08.csv"
        };

        for(String file : productFiles){
            List<Product> productFile = utils.readProducts(file);
            allProducts.addAll(productFile);
        }
        return allProducts;
    }

    public List<Map<String, Object>> getProductSubstitutes(String productId){
        List<Product> allProducts = getAllProducts();
        Product targetProduct = null;

        for(Product product : allProducts){
            if(product.getProduct_id().equals(productId)){
                 targetProduct = product;
                break;
            }
        }

        if(targetProduct == null){
            return new ArrayList<>();
        }

        double targetNormalizedPrice = calculateNormalizedPrice(targetProduct);
        String standardUnit = getStandardUnit(targetProduct.getPackage_unit());


        List<Map<String, Object>> substitutes = new ArrayList<>();

        Map<String, Object> targetProductInfo = new LinkedHashMap<>();
        targetProductInfo.put("productId", targetProduct.getProduct_id());
        targetProductInfo.put("productName", targetProduct.getProduct_name());
        targetProductInfo.put("brand", targetProduct.getStore());
        targetProductInfo.put("price", targetProduct.getPrice());
        targetProductInfo.put("quantity", targetProduct.getPackage_quantity());
        targetProductInfo.put("pricePerUnit", targetNormalizedPrice);
        targetProductInfo.put("standardUnit", standardUnit);
        targetProductInfo.put("TARGETED-PRODUCT", true);

        substitutes.add(targetProductInfo);



        for(Product product : allProducts){
            if(product.getProduct_category().equals(targetProduct.getProduct_category())
            && !product.getProduct_id().equals(productId)){

                double normalizedPrice = calculateNormalizedPrice(product);

                Map<String, Object> productInfo = new LinkedHashMap<>();
                productInfo.put("productId", product.getProduct_id());
                productInfo.put("productName", product.getProduct_name());
                productInfo.put("store", product.getStore());
                productInfo.put("price", product.getPrice());
                productInfo.put("offerDate", product.getDate());
                productInfo.put("pricePerUnit", normalizedPrice);
                productInfo.put("quantity", product.getPackage_quantity());
                productInfo.put("standardUnit", standardUnit);
                productInfo.put("isBetterValue", normalizedPrice < targetNormalizedPrice);

                substitutes.add(productInfo);
            }
        }

        substitutes.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> p1, Map<String, Object> p2) {
                double price1 = (double) p1.get("pricePerUnit");
                double price2 = (double) p2.get("pricePerUnit");
                return Double.compare(price1, price2);
            }
        });

        return substitutes;
    }

    private double calculateNormalizedPrice(Product product){
        double quantity = product.getPackage_quantity();
        String unit = product.getPackage_unit();

        if(quantity <= 0){
            return 0.0;
        }

        if(unit.equals("g")){
            quantity = quantity / 1000.0;
        } else if (unit.equals("ml")){
            quantity = quantity / 1000.0;
        }

        return product.getPrice() / quantity;
    }

    private String getStandardUnit(String originalUnit){
        if(originalUnit.equals("g") || originalUnit.equals("kg")){
            return "RON/kg";
        } else if (originalUnit.equals("ml") || originalUnit.equals("l")){
            return "RON/L";
        } else {
            return "RON/" + originalUnit;
        }
    }
}
