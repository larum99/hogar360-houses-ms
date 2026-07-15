package com.hogar360.houses.houses.domain.ports.in;

public interface RoleValidatorPort {
    String extractRole(String token);
    Long extractUserId(String token);
}
