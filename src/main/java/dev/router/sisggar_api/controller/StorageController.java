package dev.router.sisggar_api.controller;

import dev.router.sisggar_api.entity.Storage;
import dev.router.sisggar_api.entity.dto.StorageDto;
import dev.router.sisggar_api.entity.enums.Status;
import dev.router.sisggar_api.service.StorageService;
import dev.router.sisggar_api.specifications.SpecificationTemplate;
import dev.router.sisggar_api.validation.StorageValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("api/storage/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StorageController {

    @Autowired
    private StorageService storageService;

    @Autowired
    StorageValidator storageValidator;

    @DeleteMapping("/{storageId}")
    public ResponseEntity<Object> deleteStorage(@PathVariable(value="storageId") UUID storageId){
        log.debug("DELETE deleteStorage storageId received {} ", storageId);
        Optional<Storage> storageModelOptional = storageService.findById(storageId);
        if(!storageModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Storage Not Found.");
        }
        storageService.delete(storageId);
        log.debug("DELETE deleteStorage storageId deleted {} ", storageId);
        log.info("Storage deleted successfully storageId {} ", storageId);
        return ResponseEntity.status(HttpStatus.OK).body("Storage deleted successfully.");
    }

    @PutMapping("/{storageId}")
    public ResponseEntity<Object> updateStorage(@PathVariable(value="storageId") UUID storageId,
                                               @RequestBody @Valid StorageDto storageDto){
        log.debug("PUT updateStorage storageDto received {} ", storageDto.toString());
        Optional<Storage> storageModelOptional = storageService.findById(storageId);
        if(!storageModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Storage Not Found.");
        }
        var storage = storageModelOptional.get();
        storage.setDescription(storageDto.getDescription());
        storage.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        storage.setStorageLimit(storageDto.getStorageLimit());
        storageService.createStorage(storage);
        log.debug("PUT updateStorage storageId saved {} ", storage.getStorageId());
        log.info("Storage updated successfully storageId {} ", storage.getStorageId());
        return ResponseEntity.status(HttpStatus.OK).body(storage);
    }


    @PostMapping()
    public ResponseEntity<Object> createStorage(@RequestBody StorageDto storageDto, Errors errors) {

        log.debug("POST saveStorage storageDto received {} ", storageDto.toString());
        //storageValidator.validate(storageDto, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        Optional<Storage> storageModelOptional = storageService.findByDescription(storageDto.getDescription());
        if (storageModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Storage description exist!");
        }
        var storage = new Storage();
        BeanUtils.copyProperties(storageDto, storage);
        storage.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        storage.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        storage.setStorageStatus(Status.ACTIVE);
        storageService.createStorage(storage);
        log.debug("POST saveStorage storageId saved {} ", storage.getStorageId());
        log.info("Storage saved successfully storageId {} ", storage.getStorageId());
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }


    @GetMapping
    public ResponseEntity<Page<Storage>> getAllCourses(SpecificationTemplate.StorageSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "storageId", direction = Sort.Direction.ASC) Pageable pageable){

            return ResponseEntity.status(HttpStatus.OK).body(storageService.findAll(spec, pageable));

    }

    @GetMapping("/{storageId}")
    public ResponseEntity<Object> getOneStorage(@PathVariable(value="storageId") UUID storageId){
        Optional<Storage> storageModelOptional = storageService.findById(storageId);
        if(!storageModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Storage Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(storageModelOptional.get());
    }

}
