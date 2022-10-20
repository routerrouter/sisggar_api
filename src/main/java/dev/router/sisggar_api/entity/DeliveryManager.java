package dev.router.sisggar_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.router.sisggar_api.entity.dto.DeliveryManagerDto;
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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_DELIVERYMANAGER")
public class DeliveryManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID deliveryManagerId;
    @Column(nullable = false, unique = true)
    private String fullName;
    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="storage_id", nullable=false)
    private Storage storage;

    public DeliveryManager(DeliveryManagerDto managerDto) {
        var storage = new Storage();
        storage.setStorageId(managerDto.getStorageId());
        this.storage = storage;
        this.fullName = managerDto.getFullName();
        this.phoneNumber = managerDto.getPhoneNumber();
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

}
