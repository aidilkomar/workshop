package com.sein.workshop.repository;

import com.sein.workshop.dto.feature.FeatureResponseDto;
import com.sein.workshop.entity.Feature;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    @Query(value = """
//    SELECT
//        rf.role_id AS roleId,
//        f.code AS code,
//        f.name AS name,
//        f.path AS path,
//        f.sort_order AS sortOrder,
//        f.icon AS icon
//    FROM features f
//    JOIN role_features rf ON rf.feature_id = f.userId
//    WHERE rf.role_id IN (:roleIds)
//    ORDER BY rf.role_id, f.sort_order
//    """, nativeQuery = true)
//    List<FeatureResponseDto<Long>> findFeatureDtosByRoleIds(
//            @Param("roleIds") List<Long> roleIds
//    );

    Optional<Feature> findByUuid(UUID uuid);

    List<Feature> findByUuidIn(List<UUID> list);

    Page<Feature> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String search, String search1, Pageable pageable);
}
