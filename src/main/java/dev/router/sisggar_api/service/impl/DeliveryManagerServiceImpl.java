package dev.router.sisggar_api.service.impl;


import dev.router.sisggar_api.entity.DeliveryManager;
import dev.router.sisggar_api.repository.DeliveryManagerRepository;
import dev.router.sisggar_api.service.DeliveryManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

    private DeliveryManagerRepository managerRepository;

    DeliveryManagerServiceImpl(DeliveryManagerRepository managerRepository){
        this.managerRepository = managerRepository;
    }


    @Override
    public DeliveryManager createDeliveryManager(DeliveryManager deliveryManager) {
        deliveryManager = managerRepository.save(deliveryManager);
        return deliveryManager;
    }

    @Transactional
    @Override
    public void updateDeliveryManager(DeliveryManager deliveryManager) {
         managerRepository.update(deliveryManager.getDeliveryManagerId(),deliveryManager.getPhoneNumber(),deliveryManager.getFullName(),
                deliveryManager.getStorage().getStorageId());
    }

    @Transactional
    @Override
    public void delete(UUID deliveryManagerId) {
        managerRepository.delete(deliveryManagerId);
    }

    @Override
    public Optional<DeliveryManager> findById(UUID deliveryManagerId) {
        return managerRepository.findById(deliveryManagerId);
    }

    @Override
    public Optional<DeliveryManager> findByPhoneNumber(String phoneNumber) {
        return managerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Page<DeliveryManager> findAll(Specification<DeliveryManager> spec, Pageable pageable) {
        return managerRepository.findAll(spec, pageable);
    }

    @Override
    public Page<DeliveryManager> findAllByStorage(UUID storageId, Pageable pageable) {
        return managerRepository.findAllByStorage(storageId,pageable);
    }
}
