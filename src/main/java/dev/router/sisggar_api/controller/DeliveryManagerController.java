package dev.router.sisggar_api.controller;

import dev.router.sisggar_api.entity.DeliveryManager;
import dev.router.sisggar_api.entity.Storage;
import dev.router.sisggar_api.entity.dto.DeliveryManagerDto;
import dev.router.sisggar_api.entity.dto.StorageDto;
import dev.router.sisggar_api.entity.enums.Status;
import dev.router.sisggar_api.service.DeliveryManagerService;
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
@RequestMapping("api/deliveryManager/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DeliveryManagerController {

    @Autowired
    private DeliveryManagerService managerService;

    @Autowired
    private StorageService storageService;


    @PostMapping()
    public ResponseEntity<Object> createDeliveryManager(@RequestBody DeliveryManagerDto deliveryManagerDto, Errors errors) {

        log.debug("POST saveDeliveryManager deliveryManagerDto received {} ", deliveryManagerDto.toString());

        var deliveryManagerModelOptional = managerService.findByPhoneNumber(deliveryManagerDto.getPhoneNumber());
        if (deliveryManagerModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DeliveryManager phoneNumber already exist!");
        }

        var storageOptional = storageService.findById(deliveryManagerDto.getStorageId());
        if (!storageOptional.isPresent() || storageOptional.get().getStorageStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Storage not exist or is not active!");
        }

        var deliverManager = new DeliveryManager(deliveryManagerDto);
        BeanUtils.copyProperties(deliveryManagerDto, deliverManager);
        deliverManager.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        deliverManager.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        deliverManager.setStatus(Status.ACTIVE);
        managerService.createDeliveryManager(deliverManager);
        log.debug("POST saveStorage storageId saved {} ", deliverManager.getDeliveryManagerId());
        log.info("Storage saved successfully storageId {} ", deliverManager.getDeliveryManagerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(deliverManager);
    }

    @PutMapping("/{deliveryManagerId}")
    public ResponseEntity<Object> updateDeliveryManager(@PathVariable(value="deliveryManagerId") UUID deliveryManagerId,
                                                        @RequestBody @Valid DeliveryManagerDto deliveryManagerDto){
        log.debug("PUT updateDeliveryManager DeliveryManagerDto received {} ", deliveryManagerDto.toString());
        var deliveryManagerOptional = managerService.findById(deliveryManagerId);
        if(!deliveryManagerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeliveryManagerOptional Not Found.");
        }

        var storageOptional = storageService.findById(deliveryManagerDto.getStorageId());
        if (!storageOptional.isPresent() || storageOptional.get().getStorageStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Storage not exist or is not active!");
        }

        var deliveryManager = deliveryManagerOptional.get();
        BeanUtils.copyProperties(deliveryManagerDto, deliveryManager);
        deliveryManager.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        deliveryManager.getStorage().setStorageId(deliveryManagerDto.getStorageId());
        managerService.updateDeliveryManager(deliveryManager);
        log.debug("PUT updateDeliveryManager deliveryManagerId saved {} ", deliveryManager.getDeliveryManagerId());
        log.info("DeliveryManager updated successfully deliveryManagerId {} ", deliveryManager.getDeliveryManagerId());
        return ResponseEntity.status(HttpStatus.OK).body(deliveryManager);
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryManager>> getAllDeliveryManagers(SpecificationTemplate.DeliveryManagerSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "deliveryManagerId", direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(managerService.findAll(spec, pageable));

    }

    @GetMapping("/{deliveryManagerId}")
    public ResponseEntity<Object> getOneDeliveryManager(@PathVariable(value="deliveryManagerId") UUID deliveryManagerId){
        var deliveryManagerOptional = managerService.findById(deliveryManagerId);
        if(!deliveryManagerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeliveryManager Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(deliveryManagerOptional.get());
    }

    @DeleteMapping("/{deliveryManagerId}")
    public ResponseEntity<Object> deleteDeliveryManager(@PathVariable(value="deliveryManagerId") UUID deliveryManagerId){
        log.debug("DELETE deleteDeliveryManager deliveryManagerId received {} ", deliveryManagerId);
        var deliveryManagerOptional = managerService.findById(deliveryManagerId);
        if(!deliveryManagerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeliveryManager Not Found.");
        }
        managerService.delete(deliveryManagerId);
        log.debug("DELETE deleteDeliveryManager deliveryManagerId deleted {} ", deliveryManagerId);
        log.info("DeliveryManager deleted successfully deliveryManagerId {} ", deliveryManagerId);
        return ResponseEntity.status(HttpStatus.OK).body("DeliveryManager deleted successfully.");
    }




}
