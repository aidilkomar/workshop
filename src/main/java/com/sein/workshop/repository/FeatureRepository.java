package com.sein.workshop.repository;

import com.sein.workshop.entity.Feature;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    @Query("""
        select distinct f
        from UserRole ur
        join RoleFeature rf on rf.role = ur.role
        join rf.feature f
        where ur.user.id = :userId
        order by f.sortOrder
    """)
    List<Feature> findFeaturesByUserId(Long userId);

    boolean existsByCode(@NotBlank(message = "code is required") String code);

    Optional<Feature> findByUuid(UUID uuid);

    List<Feature> findByUuidIn(List<UUID> list);

    Page<Feature> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String search, String search1, Pageable pageable);
}
