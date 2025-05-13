package com.example.pricecomparator.Controllers;


import com.example.pricecomparator.Model.Product;
import com.example.pricecomparator.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class ProductController {
    private final Utils utils;

    public ProductController(Utils utils){
        this.utils = utils;
    }

    @GetMapping("/lidl")
    public List<Product> getProducts(){
        return utils.readProducts("lidl_2025-05-01.csv");
    }
}
