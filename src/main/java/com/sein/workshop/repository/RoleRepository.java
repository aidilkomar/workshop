package com.sein.workshop.repository;

import com.sein.workshop.dto.role.RoleResponseDto;
import com.sein.workshop.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByCode(String code);

    Page<Role> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String search, String search1, Pageable pageable);

    @Query(value = """
        SELECT
            r.uuid AS uuid,
            r.code AS code,
            r.name AS name
        FROM roles r
        JOIN user_roles ur ON ur.role_id = r.id
        JOIN users u ON u.id = ur.user_id
        WHERE u.uuid = :uuid
    """, nativeQuery = true)
    List<RoleResponseDto> findRoleDtosByUserUuid(@Param("uuid") UUID uuid);

    List<Role> findAllByUuidIn(List<UUID> uuids);

    Optional<Role> findByUuid(UUID uuid);
}
