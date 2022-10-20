package dev.router.sisggar_api.service.impl;


import dev.router.sisggar_api.entity.Storage;
import dev.router.sisggar_api.repository.StorageRepository;
import dev.router.sisggar_api.service.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private StorageRepository storageRepository;

    StorageServiceImpl(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
    }


    @Override
    public Storage createStorage(Storage storage) {
        storage = storageRepository.save(storage);
        return storage;
    }

    @Override
    public Storage updateStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    @Transactional
    @Override
    public void delete(UUID storageId) {
        storageRepository.delete(storageId);
    }

    @Override
    public Optional<Storage> findById(UUID storageId) {
        return storageRepository.findById(storageId);
    }

    @Override
    public Optional<Storage> findByDescription(String description) {
        return storageRepository.findByDescription(description);
    }

    @Override
    public Page<Storage> findAll(Specification<Storage> spec, Pageable pageable) {
        return storageRepository.findAll(spec, pageable);
    }
}
