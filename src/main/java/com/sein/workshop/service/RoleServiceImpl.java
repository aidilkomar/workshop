package com.sein.workshop.service;

import com.sein.workshop.dto.role.RoleCreateDto;
import com.sein.workshop.entity.Role;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.repository.RoleRepository;
import com.sein.workshop.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void create(RoleCreateDto req) {
        var principal = SecurityUtils.getCurrentUser();

        var existCode = roleRepository.existsByCode(req.code());
        if (existCode) {
            throw new ConflictException("The code already exist");
        }

        var entity = new Role();
        entity.setCode(req.code());
        entity.setName(req.name());
        entity.setCreatedBy(principal != null ? principal.id().toString() : null);

        roleRepository.save(entity);
    }
}
