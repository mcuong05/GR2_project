package com.shopmanager.backend.auth;

import com.shopmanager.backend.common.exception.AppException;
import com.shopmanager.backend.user.User;
import com.shopmanager.backend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {
        // Cùng một thông báo cho "không có tài khoản" và "sai mật khẩu" để không lộ tài khoản nào tồn tại
        User user = userRepository.findByUsername(request.username().trim())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                .orElseThrow(() -> new AppException(HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS", "Sai tên đăng nhập hoặc mật khẩu"));
        if (!user.isActive()) {
            throw new AppException(HttpStatus.FORBIDDEN, "ACCOUNT_DISABLED",
                    "Tài khoản đã bị khóa");
        }
        TokenService.IssuedToken token = tokenService.issue(user);
        return new LoginResponse(token.value(), "Bearer", token.expiresInSeconds(),
                UserInfo.from(user));
    }

    public UserInfo currentUser(Long userId) {
        return userRepository.findById(userId)
                .filter(User::isActive)
                .map(UserInfo::from)
                .orElseThrow(() -> new AppException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED",
                        "Phiên đăng nhập không còn hợp lệ"));
    }
}