package dev.router.sisggar_api.repository;

import dev.router.sisggar_api.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LocationRepository  extends JpaRepository<Location, UUID> , JpaSpecificationExecutor<Location> {
    @Modifying
    @Query(value = "UPDATE tb_location set location_status='INACTIVE' WHERE location_id = :locationId", nativeQuery = true)
    void delete(@Param("locationId") UUID locationId);

    Optional<Location> findByDescription(@Param("description") String description);
}
