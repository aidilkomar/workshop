package com.sein.workshop.service;

import com.sein.workshop.dto.auth.LoginRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    String login(@Valid LoginRequest req);
}
