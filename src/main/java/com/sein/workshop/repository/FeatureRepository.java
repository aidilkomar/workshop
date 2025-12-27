package com.sein.workshop.repository;

import com.sein.workshop.entity.Feature;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
