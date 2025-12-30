package com.sein.workshop.repository;

import com.sein.workshop.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("""
        select r.code
        from UserRole ur
        join ur.role r
        where ur.user.id = :user_id
    """)
    List<String> findRoleCodesByUserId(@Param("user_id") Long userId);

    @Query(value = """
       select ur.role_id
       from user_roles ur
       where ur.user_id = :user_id
    """, nativeQuery = true)
    Set<Long> findRoleIdsByUserId(@Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query(value = """
        delete from user_roles ur
        where ur.user_id = :user_id
    """, nativeQuery = true)
    void deleteByUserId(@Param("user_id") Long userId);
}
