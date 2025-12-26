package com.sein.workshop.repository;

import com.sein.workshop.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Query("""
        select distinct p.code
        from UserRole ur
        join ur.role r
        join RolePermission rp on rp.role = r
        join rp.permission p
        where ur.user.id = :userId
    """)
    List<String> findPermissionCodesByUserId(@Param("userId") Long userId);
}

