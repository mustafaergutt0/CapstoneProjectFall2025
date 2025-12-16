package com.ByteCraft.IotProject.Entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "device_status")
public class DeviceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id (pk)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device; // device_id (foreign key)

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen; // is_open

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime; // updated_time
}

