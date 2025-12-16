package com.ByteCraft.IotProject.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User",
        indexes = {
                @Index(name = "idx_person_email", columnList = "email", unique = true),
                @Index(name = "idx_person_phone", columnList = "phone_number", unique = true)

        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Zorunlu alanlar
    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    private String surname;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    // Opsiyonel / değişebilir alanlar
    @Column(length = 255)
    private String address;

    @Column(name = "phone_number", unique = true, length = 20)
    private String phoneNumber;

    // Kayıt tarihi
    @CreationTimestamp
    @Column(name = "register_date", updatable = false)
    private LocalDateTime registerDate;

    @Column(name = "number_of_device")
    private Integer numberOfDevice;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Device> devices = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;



}

