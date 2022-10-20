package dev.router.sisggar_api.service;

import dev.router.sisggar_api.entity.DeliveryManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerService {
    DeliveryManager createDeliveryManager(DeliveryManager deliveryManager);
    void updateDeliveryManager(DeliveryManager deliveryManager);
    void delete(UUID deliveryManagerId);
    Optional<DeliveryManager> findById(UUID deliveryManagerId);
    Optional<DeliveryManager> findByPhoneNumber(String phoneNumber);
    Page<DeliveryManager> findAll(Specification<DeliveryManager> spec, Pageable pageable);
    Page<DeliveryManager> findAllByStorage(UUID storageId, Pageable pageable);
}
