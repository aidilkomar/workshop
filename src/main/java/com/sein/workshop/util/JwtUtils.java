package com.sein.workshop.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtils {
    @Getter @Setter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter @Setter
    @Value("${jwt.expire-time}")
    private Duration expiredTime;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, Long id, UUID uuid, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .subject(username)
                .claim("id", id.toString())
                .claim("uid", uuid.toString())
//                .claim("roles", roles.stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .toList()
//                )
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiredTime.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Long getId(String token) {
        String id = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);

        return Long.parseLong(id);
    }

    public UUID getUserUuid(String token) {
        String uid = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("uid", String.class);

        return UUID.fromString(uid);
    }

    public Collection<? extends GrantedAuthority> getRoles(String token) {
        @SuppressWarnings("unchecked")
        List<String> roles = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        @SuppressWarnings("unchecked")
        List<String> perms = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("perms", List.class);

        return perms.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
