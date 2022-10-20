package dev.router.sisggar_api.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class DeliveryManagerDto {

    @NotBlank
    private String fullName;
    @NotBlank
    private String phoneNumber;

    private UUID storageId;
}
