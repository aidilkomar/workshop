package com.sein.workshop.repository;

import com.sein.workshop.entity.Role;
import com.sein.workshop.entity.RoleFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleFeatureRepository extends JpaRepository<RoleFeature, Long> {

    @Query(value = """
        SELECT DISTINCT upper(f.code || '_read') AS permission
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_read = true
    
        UNION ALL
    
        SELECT DISTINCT upper(f.code || '_write')
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_write = true
    
        UNION ALL
    
        SELECT DISTINCT upper(f.code || '_update')
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_update = true
    
        UNION ALL
    
        SELECT DISTINCT upper(f.code || '_delete')
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_delete = true
    
        UNION ALL
    
        SELECT DISTINCT upper(f.code || '_APPROVE')
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_approve = true
    
        UNION ALL
    
        SELECT DISTINCT upper(f.code || '_EXPORT')
        FROM user_roles ur
        JOIN role_features rf ON rf.role_id = ur.role_id
        JOIN features f ON f.id = rf.feature_id
        WHERE ur.user_id = :user_id
          AND rf.can_export = true
    """, nativeQuery = true)
    List<String> findPermissionByUserId(@Param("user_id") Long userId);


    List<RoleFeature> findByRole(Role role);
}
