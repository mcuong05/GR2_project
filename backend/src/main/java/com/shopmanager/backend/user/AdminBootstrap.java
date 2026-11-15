package com.shopmanager.backend.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          @Value("${app.bootstrap.admin-username:admin}") String adminUsername,
                          @Value("${app.bootstrap.admin-password:}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            return;
        }
        if (adminPassword == null || adminPassword.isBlank()) {
            log.warn("Chưa có người dùng nào và chưa cấu hình app.bootstrap.admin-password, bỏ qua tạo tài khoản đầu tiên");
            return;
        }
        User admin = new User(adminUsername, passwordEncoder.encode(adminPassword),
                "Chủ cửa hàng", Role.MANAGER);
        userRepository.save(admin);
        log.info("Đã tạo tài khoản quản lý đầu tiên: {}", adminUsername);
    }
}