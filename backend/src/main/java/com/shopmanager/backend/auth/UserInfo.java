package com.shopmanager.backend.auth;

import com.shopmanager.backend.user.User;

public record UserInfo(Long id, String username, String fullName, String role) {

    public static UserInfo from(User user) {
        return new UserInfo(user.getId(), user.getUsername(), user.getFullName(),
                user.getRole().name());
    }
}