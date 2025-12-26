package com.sein.workshop.repository;

import com.sein.workshop.entity.RoleFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleFeatureRepository extends JpaRepository<RoleFeature, Long> {

}
