package dev.router.sisggar_api.repository;

import dev.router.sisggar_api.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StorageRepository extends JpaRepository<Storage, UUID>, JpaSpecificationExecutor<Storage> {
    @Modifying
    @Query(value = "UPDATE tb_storage set storage_status='INACTIVE' WHERE storage_id= :storageId", nativeQuery = true)
    void delete(@Param("storageId") UUID storageId);

    Optional<Storage> findByDescription(@Param("description") String description);

}
