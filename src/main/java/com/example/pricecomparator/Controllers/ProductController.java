package com.example.pricecomparator.Controllers;


import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Services.BasketService;
import com.example.pricecomparator.Services.PriceHistoryService;
import com.example.pricecomparator.Services.ProductService;
import com.example.pricecomparator.Utils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final BasketService basketService;
    private final PriceHistoryService priceHistoryService;


    public ProductController(
            ProductService productService,
            BasketService basketService,
            PriceHistoryService priceHistoryService
    ){
        this.productService = productService;
        this.basketService = basketService;
        this.priceHistoryService = priceHistoryService;
    }


    @GetMapping("/{productId}/substitutes")
    public List<Map<String, Object>> getProductSubstitutes(@PathVariable String productId){
        return productService.getProductSubstitutes(productId);
    }

    @PostMapping("basket/optimize")
    public Map<String, Object> optimizeShoppingBasket(@RequestBody List<String> productIds){
        return basketService.optimizeBasket(productIds);
    }

    @GetMapping("{productId}/price-history")
    public Map<String, Object> getPriceHistory(
            @PathVariable String productId,
            @RequestParam(required = false) String store
    ){
        return priceHistoryService.getPriceHistory(productId, store);
    }
}
