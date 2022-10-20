package dev.router.sisggar_api.entity.dto;

import dev.router.sisggar_api.entity.enums.Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Data
public class LocationDto {


    @NotBlank
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status locationStatus;
    
    private UUID storageId;

}
