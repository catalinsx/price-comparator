package com.example.pricecomparator.Repository;

import com.example.pricecomparator.Model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Integer> {
    List<PriceAlert> findByUserId(String userId);
}
