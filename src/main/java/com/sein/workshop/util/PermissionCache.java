package com.sein.workshop.util;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PermissionCache {
    private final Map<String, Set<String>> cache = new ConcurrentHashMap<>();

    public  Set<String> getPermission(String key) {
        return  cache.getOrDefault(key, Set.of());
    }

    public void setPermission(String key, Set<String> permissions) {
        cache.put(key, permissions);
    }

    public void clearPermissions(String key) {
        cache.remove(key);
    }
}
