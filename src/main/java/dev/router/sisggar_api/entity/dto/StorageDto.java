package dev.router.sisggar_api.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StorageDto {

    @NotBlank
    private String description;
    private Integer storageLimit;

}
