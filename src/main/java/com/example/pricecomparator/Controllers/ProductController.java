package com.example.pricecomparator.Controllers;


import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Services.ProductService;
import com.example.pricecomparator.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @GetMapping("/{productId}/substitutes")
    public List<Map<String, Object>> getProductSubstitutes(@PathVariable String productId){
        return productService.getProductSubstitutes(productId);
    }
}
