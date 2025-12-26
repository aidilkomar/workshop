package com.sein.workshop.repository;

import com.sein.workshop.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("""
        select r.code
        from UserRole ur
        join ur.role r
        where ur.user.id = :user_id
    """)
    List<String> findRoleCodesByUserId(@Param("user_id") Long userId);
}
