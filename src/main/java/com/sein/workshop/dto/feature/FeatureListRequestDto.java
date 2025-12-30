package com.sein.workshop.dto.feature;

import com.sein.workshop.dto.PagedRequest;

public record FeatureListRequestDto(
        PagedRequest pagedRequest,
        String search
) {
}
