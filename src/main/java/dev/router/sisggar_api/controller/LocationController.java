package dev.router.sisggar_api.controller;

import dev.router.sisggar_api.entity.Location;
import dev.router.sisggar_api.entity.Location;
import dev.router.sisggar_api.entity.Storage;
import dev.router.sisggar_api.entity.dto.LocationDto;
import dev.router.sisggar_api.entity.dto.LocationDto;
import dev.router.sisggar_api.entity.dto.StorageDto;
import dev.router.sisggar_api.entity.enums.Status;
import dev.router.sisggar_api.service.LocationService;
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
@RequestMapping("api/location/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private StorageService storageService;


    @DeleteMapping("/{locationId}")
    public ResponseEntity<Object> deleteStorage(@PathVariable(value="locationId") UUID locationId){
        log.debug("DELETE deleteLocation locationId received {} ", locationId);
        Optional<Location> locationModelOptional = locationService.findById(locationId);
        if(!locationModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location Not Found.");
        }
        locationService.delete(locationId);
        log.debug("DELETE deleteLocation locationId deleted {} ", locationId);
        log.info("Location deleted successfully locationId {} ", locationId);
        return ResponseEntity.status(HttpStatus.OK).body("Location deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<Page<Location>> getAllLocations(SpecificationTemplate.LocationSpec spec,
                                                        @PageableDefault(page = 0, size = 10, sort = "locationId", direction = Sort.Direction.ASC) Pageable pageable){

            return ResponseEntity.status(HttpStatus.OK).body(locationService.findAll(spec, pageable));

    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Object> getLocation(@PathVariable(value="locationId") UUID locationId){
        Optional<Location> locationModelOptional = locationService.findById(locationId);
        if(!locationModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(locationModelOptional.get());
    }

    @PostMapping()
    public ResponseEntity<Object> createLocation(@RequestBody LocationDto locationDto, Errors errors) {

        log.debug("POST saveLocation locationDto received {} ", locationDto.toString());

        var locationOptional = locationService.findByDescription(locationDto.getDescription());
        if (locationOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Location description already exist!");
        }

        var storageOptional = storageService.findById(locationDto.getStorageId());
        if (!storageOptional.isPresent() || storageOptional.get().getStorageStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Storage not exist or is not active!");
        }

        var location = new Location(locationDto);
        BeanUtils.copyProperties(locationDto, location);
        location.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        location.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        location.setLocationStatus(Status.ACTIVE);
        locationService.createLocation(location);
        log.debug("POST saveLocation locationId saved {} ", location.getLocationId());
        log.info("Location saved successfully locationId {} ", location.getLocationId());
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Object> updateLocation(@PathVariable(value="locationId") UUID locationId,
                                                        @RequestBody @Valid LocationDto locationDto){
        log.debug("PUT updateLocation LocationDto received {} ", locationDto.toString());
        var locationOptional = locationService.findById(locationId);
        if(!locationOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LocationOptional Not Found.");
        }

        var storageOptional = storageService.findById(locationDto.getStorageId());
        if (!storageOptional.isPresent() || storageOptional.get().getStorageStatus().equals(Status.INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Storage not exist or is not active!");
        }

        var location = locationOptional.get();
        BeanUtils.copyProperties(locationDto, location);
        location.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        location.getStorage().setStorageId(locationDto.getStorageId());
        locationService.updateLocation(location);
        log.debug("PUT updateLocation LocationId saved {} ", location.getLocationId());
        log.info("Location updated successfully LocationId {} ", location.getLocationId());
        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

}
