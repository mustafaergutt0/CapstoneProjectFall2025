package com.ByteCraft.IotProject.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "device_info")
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // id (pk)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;          // DeviceId (foreign key)

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "flow_lpm", nullable = false)
    private Double flowLpm;

    @Column(name = "volume_l", nullable = false)
    private Double volumeL;

    @Column(nullable = false, length = 30)
    private String mode;
}

