package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.feature.FeatureCreateDto;
import com.sein.workshop.service.FeatureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping("/create")
//    @PreAuthorize("@authz.hasPermission('user_read')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid FeatureCreateDto req) throws Exception {
        featureService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("feature has created", req, LocalDateTime.now())
        );
    }
}
