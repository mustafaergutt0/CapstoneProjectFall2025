package com.ByteCraft.IotProject.Entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "devices",
        indexes = {
                @Index(name = "idx_device_user_id", columnList = "user_id"),
                @Index(name = "idx_device_status", columnList = "status")
        })
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bir cihazın sahibi tek kullanıcıdır
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false) // FK
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String location;

    @Column(nullable = false, length = 30)
    private String status; // örn: ACTIVE / PASSIVE

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
