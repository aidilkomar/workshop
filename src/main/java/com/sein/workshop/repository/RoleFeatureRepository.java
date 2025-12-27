package com.sein.workshop.repository;

import com.sein.workshop.entity.RoleFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleFeatureRepository extends JpaRepository<RoleFeature, Long> {

    @Query(value = """
        SELECT DISTINCT
          f.code || ':' || rfa.action AS permission
        FROM user_roles ur
        JOIN role_feature_actions rfa ON rfa.role_id = ur.role_id
        JOIN features f ON f.id = rfa.feature_id
        WHERE ur.user_id = :user_id
    """, nativeQuery = true)
    List<String> findPermissionByUserId(@Param("user_id") Long userId);
}
