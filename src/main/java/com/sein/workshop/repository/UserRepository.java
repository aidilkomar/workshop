package com.sein.workshop.repository;

import com.sein.workshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
