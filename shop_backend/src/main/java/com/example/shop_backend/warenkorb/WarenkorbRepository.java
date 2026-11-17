package com.example.shop_backend.warenkorb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarenkorbRepository extends JpaRepository<Warenkorb, Long> {
    List<Warenkorb> findByClientId(String clientId);




}
