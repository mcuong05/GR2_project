package com.shopmanager.backend.auth;

public record LoginResponse(String accessToken, String tokenType, long expiresInSeconds,
                            UserInfo user) {
}