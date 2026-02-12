package com.sein.workshop.repository;

import com.sein.workshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u
                from User u
                where u.username = :username
                  and u.deletedAt is null
            """)
    Optional<User> findByUsername(@Param("username") String username);

    @Query("""
            select u from User u where u.email = :email and u.deletedAt is null
            """)
    Optional<User> findByEmail(@Param("email") String email);

    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);

    Optional<User> findByUuid(UUID uuid);
}
