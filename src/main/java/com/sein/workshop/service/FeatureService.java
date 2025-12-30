package com.sein.workshop.service;

import com.sein.workshop.dto.NavigationResponseDto;
import com.sein.workshop.dto.feature.FeatureCreateDto;
import com.sein.workshop.dto.feature.FeatureResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeatureService {
    void create(FeatureCreateDto req);

    Page<FeatureResponseDto> getFeatures(Pageable pageable, String search);

    List<NavigationResponseDto> getNavigations();
}
