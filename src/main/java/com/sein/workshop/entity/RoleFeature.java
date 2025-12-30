package com.sein.workshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "role_features",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"role_id", "feature_id"})
        }
)
@Getter
@Setter
public class RoleFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    @Column(name = "can_read", nullable = false)
    private boolean canRead;

    @Column(name = "can_write", nullable = false)
    private boolean canWrite;

    @Column(name = "can_update", nullable = false)
    private boolean canUpdate;

    @Column(name = "can_delete", nullable = false)
    private boolean canDelete;

    @Column(name = "can_approve", nullable = false)
    private boolean canApprove;

    @Column(name = "can_export", nullable = false)
    private boolean canExport;
}


