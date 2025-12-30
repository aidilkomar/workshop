package com.sein.workshop.service;

import com.sein.workshop.dto.NavigationResponseDto;
import com.sein.workshop.dto.feature.FeatureCreateDto;
import com.sein.workshop.dto.feature.FeatureResponseDto;
import com.sein.workshop.entity.Feature;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.repository.FeatureRepository;
import com.sein.workshop.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public List<NavigationResponseDto> getNavigations() {
        var principal = SecurityUtils.getCurrentUser();
        if (principal == null) {
            return List.of();
        }
        var navigations = featureRepository.findFeaturesByUserId(principal.userId());
        return navigations.stream()
                .map(nav -> new NavigationResponseDto(nav.getName(), nav.getPath(), nav.getIcon(), nav.getSortOrder()))
                .toList();
    }

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

    @Override
    public Page<FeatureResponseDto> getFeatures(Pageable pageable, String search) {
        Page<Feature> features = (search == null || search.isBlank()) ?
                featureRepository.findAll(pageable)
                : featureRepository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);
        return features.map(f ->
                new FeatureResponseDto(
                        f.getUuid(),
                        f.getCode(),
                        f.getName(),
                        f.getPath(),
                        f.getSortOrder(),
                        f.getIcon()
                ));
    }
}
