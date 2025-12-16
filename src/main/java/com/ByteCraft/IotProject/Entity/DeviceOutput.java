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
@Table(name = "device_output")
public class DeviceOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OutputAction action;   // INCREASE / DECREASE / OPEN / CLOSE

    @Column(nullable = false, length = 20)
    private String status;         // "20 birim arttı" gibi kısa log
}
