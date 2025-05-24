package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.PriceAlert;
import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Repository.PriceAlertRepository;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlertService {
    private final Utils utils;
    private final PriceAlertRepository priceAlertRepository;

    public AlertService(Utils utils, PriceAlertRepository priceAlertRepository){
        this.utils = utils;
        this.priceAlertRepository = priceAlertRepository;
    }

    private List<Product> getAllProducts(){
        String productFile = "lidl_2025-05-01.csv";
        return utils.readProducts(productFile);
    }

    public void addAlert(String userId, String productId, double target){
        priceAlertRepository.save(new PriceAlert(userId, productId, target));
    }

    public List<Map<String, Object>> getAlerts(String userId){
        List<Product> products = getAllProducts();
        // fetch alerts for the specified userid
        List<PriceAlert> alerts = priceAlertRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        //foreach saved alert find the matching product and see if it's triggered or not
        for(PriceAlert priceAlert : alerts){
            for(Product product : products){
                if(product.getProduct_id().equals(priceAlert.getProductId())){
                    double currentPrice = product.getPrice();
                    boolean triggered = currentPrice <= priceAlert.getTargetPrice();

                    Map<String, Object> info = new LinkedHashMap<>();
                    info.put("productId", priceAlert.getProductId());
                    info.put("productName", product.getProduct_name());
                    info.put("currentPrice", currentPrice);
                    info.put("targetPrice", priceAlert.getTargetPrice());
                    info.put("triggered", triggered);

                    result.add(info);
                    break;
                }
            }
        }
        return result;
    }


}
