package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.NavigationResponseDto;
import com.sein.workshop.dto.feature.FeatureCreateDto;
import com.sein.workshop.dto.feature.FeatureListRequestDto;
import com.sein.workshop.dto.feature.FeatureResponseDto;
import com.sein.workshop.service.FeatureService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/nav")
    public ResponseEntity<ApiResponse<List<NavigationResponseDto>>> getNavigation() {
        var results = featureService.getNavigations();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "success retrieve navigations",
                        results,
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/")
    @PreAuthorize("@authz.hasPermission('ADMIN_READ')")
    public ResponseEntity<ApiResponse<List<FeatureResponseDto>>> getAll(@RequestBody @Valid FeatureListRequestDto req) {
        int page = Math.max(req.pagedRequest().page() - 1, 0);
        Pageable pageable = PageRequest.of(
                page,
                req.pagedRequest().size(),
                Sort.by(
                                req.pagedRequest().sortDirection() != null ? req.pagedRequest().sortBy() : "name")
                        .ascending()
        );

        Page<FeatureResponseDto> pageResult = featureService.getFeatures(pageable, req.search());
        return ResponseEntity.ok(
                ApiResponse.listSuccess(
                        "retrieve features successfully",
                        pageResult.stream().toList(),
                        pageResult.getTotalElements(),
                        pageResult.getTotalPages(),
                        req.pagedRequest().page(),
                        req.pagedRequest().size()
                )
        );
    }

    @PostMapping("/create")
    @PreAuthorize("@authz.hasPermission('ADMIN_CREATE')")
    public ResponseEntity<ApiResponse<FeatureCreateDto>> create(@RequestBody @Valid FeatureCreateDto req) {
        featureService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("feature has created", req, LocalDateTime.now())
        );
    }
}
