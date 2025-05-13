package com.example.pricecomparator.Services;

import com.example.pricecomparator.Model.Discount;
import com.example.pricecomparator.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BestDiscount {
    private final Utils utils;

    public BestDiscount(Utils utils){
        this.utils = utils;
    }

    private List<Discount> getAllDiscounts(){
        List<Discount> allDiscounts = new ArrayList<>();

        String[] discountFiles = {
                "lidl_discounts_2025-05-01.csv",
                "lidl_discounts_2025-05-08.csv",
                "kaufland_discounts_2025-05-01.csv",
                "kaufland_discounts_2025-05-08.csv",
                "profi_discounts_2025-05-01.csv",
                "profi_discounts_2025-05-08.csv"
        };

        for(String file : discountFiles){
            List<Discount> discountFile= utils.readDiscounts(file);
            allDiscounts.addAll(discountFile);
        }
        return allDiscounts;

    }

    public List<Discount> getBestDiscounts(@RequestParam(defaultValue = "10") int limit){
        List<Discount> allDiscounts = getAllDiscounts();

        allDiscounts.sort(new Comparator<>() {
            @Override
            public int compare(Discount d1, Discount d2) {
                return Double.compare(d2.getPercentage_of_discount(), d1.getPercentage_of_discount());
            }
        });

        List<Discount> bestDiscounts = new ArrayList<>();
        int count = 0;
        for(Discount discount : allDiscounts){
            if(count < limit){
                bestDiscounts.add(discount);
                count++;
            } else {
                break;
            }
        }
        return bestDiscounts;
    }
}
