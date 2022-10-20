package dev.router.sisggar_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.router.sisggar_api.entity.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_STORAGE")
public class Storage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID storageId;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private Integer storageLimit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status storageStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="storage")
    private Set<Location> locations;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="storage")
    private Set<DeliveryManager> managers;
}
