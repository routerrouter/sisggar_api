package dev.router.sisggar_api.service;

import dev.router.sisggar_api.entity.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface StorageService {
    Storage createStorage(Storage storage);
    Storage updateStorage(Storage storageDto);
    void delete(UUID storageId);
    Optional<Storage> findById(UUID storageId);
    Optional<Storage> findByDescription(String description);
    Page<Storage> findAll(Specification<Storage> spec, Pageable pageable);
}
