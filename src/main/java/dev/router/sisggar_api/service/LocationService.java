package dev.router.sisggar_api.service;

import dev.router.sisggar_api.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LocationService {
    Location createLocation(Location location);
    Location updateLocation(Location location);
    void delete(UUID locationId);
    Optional<Location> findById(UUID locationId);
    Optional<Location> findByDescription(String description);
    Page<Location> findAll(Specification<Location> spec, Pageable pageable);
}
