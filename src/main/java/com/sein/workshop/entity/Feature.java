package com.sein.workshop.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "features")
@Getter @Setter
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private String path;

    private Integer sortOrder;

    private String icon;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UuidCreator.getTimeOrderedEpoch();
        }
    }
}