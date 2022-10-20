package dev.router.sisggar_api.repository;

import dev.router.sisggar_api.entity.DeliveryManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID>, JpaSpecificationExecutor<DeliveryManager> {
    @Modifying
    @Query(value = "UPDATE tb_deliverymanager set status='INACTIVE' WHERE delivery_manager_Id = :deliveryManagerId", nativeQuery = true)
    void delete(@Param("deliveryManagerId") UUID deliveryManagerId);

    @Modifying
    @Query(value = "UPDATE tb_deliverymanager set full_name=:fullName, phone_number=:phoneNumber, storage_id=:storageId WHERE delivery_manager_Id = :deliveryManagerId", nativeQuery = true)
    void update(@Param("deliveryManagerId") UUID deliveryManagerId, String phoneNumber, String fullName, UUID storageId);

    @Query(value = "SELECT  * from tb_deliverymanager WHERE storage_id = :storageId", nativeQuery = true)
    Page<DeliveryManager> findAllByStorage(@Param("storageId") UUID storageId, Pageable pageable);

    Optional<DeliveryManager> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
