package dev.router.sisggar_api.service.impl;

import dev.router.sisggar_api.entity.Location;
import dev.router.sisggar_api.repository.LocationRepository;
import dev.router.sisggar_api.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {
    private LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Location createLocation(Location location) {
        return repository.save(location);
    }

    @Override
    public Location updateLocation(Location location) {
        return repository.save(location);
    }

    @Transactional
    @Override
    public void delete(UUID locationId) {
        repository.delete(locationId);
    }

    @Override
    public Optional<Location> findById(UUID locationId) {
        return repository.findById(locationId);
    }

    @Override
    public Optional<Location> findByDescription(String description) {
        return repository.findByDescription(description);
    }

    @Override
    public Page<Location> findAll(Specification<Location> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
