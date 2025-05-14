package com.example.pricecomparator.Controllers;

import com.example.pricecomparator.Model.Discount;
import com.example.pricecomparator.Services.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService){
        this.discountService = discountService;
    }

    @GetMapping("/best-discount")
   public List<Discount> getBestDiscounts(
           @RequestParam(defaultValue = "10") int limit
   ) {
        return discountService.getBestDiscounts(limit);
   }

   @GetMapping("/new-discount")
    public List<Discount> getNewDiscounts(){
        return discountService.getNewDiscounts();
   }
}
