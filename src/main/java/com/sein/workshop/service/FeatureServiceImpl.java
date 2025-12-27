package com.sein.workshop.service;

import com.sein.workshop.dto.feature.FeatureCreateDto;
import com.sein.workshop.entity.Feature;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.repository.FeatureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public void create(FeatureCreateDto req) {
        var existCode = featureRepository.existsByCode(req.code());

        if(existCode) {
            throw new ConflictException("The code already exist");
        }

        var entity = new Feature();
        entity.setCode(req.code());
        entity.setIcon(req.icon());
        entity.setName(req.name());
        entity.setPath(req.path());
        entity.setSortOrder(req.sortOrder());

        featureRepository.save(entity);
    }
}
