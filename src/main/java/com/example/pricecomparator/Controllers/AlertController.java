package com.example.pricecomparator.Controllers;


import com.example.pricecomparator.Services.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertService;
    public AlertController(AlertService alertService){
        this.alertService = alertService;
    }

    @PostMapping
    public String addAlert(@RequestBody Map<String, Object> require){
        String userId = (String) require.get("userId");
        String productId = (String) require.get("product");
        double target = ((Number) require.get("target")).doubleValue();

        alertService.addAlert(userId, productId, target);
        return "Alert added successfully";
    }

    @GetMapping
    public List<Map<String, Object>> getAlerts(@RequestParam("userId") String userId){
        return alertService.getAlerts(userId);
    }
}
