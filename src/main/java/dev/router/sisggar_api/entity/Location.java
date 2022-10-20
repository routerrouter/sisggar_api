package dev.router.sisggar_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.router.sisggar_api.entity.dto.DeliveryManagerDto;
import dev.router.sisggar_api.entity.dto.LocationDto;
import dev.router.sisggar_api.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_LOCATION")
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID locationId;

    @Column(nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status locationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="storage_id", nullable=false)
    private Storage storage;


    public Location(LocationDto locationDto) {
        var storage = new Storage();
        storage.setStorageId(locationDto.getStorageId());
        this.storage = storage;
        this.description = locationDto.getDescription();
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

}
