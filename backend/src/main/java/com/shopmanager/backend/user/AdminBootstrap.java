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
    private final String staffUsername;
    private final String staffPassword;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          @Value("${app.bootstrap.admin-username:admin}") String adminUsername,
                          @Value("${app.bootstrap.admin-password:}") String adminPassword,
                          @Value("${app.bootstrap.staff-username:staff}") String staffUsername,
                          @Value("${app.bootstrap.staff-password:}") String staffPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        createIfMissing(adminUsername, adminPassword, "Chủ cửa hàng", Role.MANAGER);
        createIfMissing(staffUsername, staffPassword, "Nhân viên mẫu", Role.STAFF);
    }

    private void createIfMissing(String username, String password, String fullName, Role role) {
        if (password == null || password.isBlank() || userRepository.existsByUsername(username)) {
            return;
        }
        userRepository.save(new User(username, passwordEncoder.encode(password), fullName, role));
        log.info("Đã tạo tài khoản {}: {}", role, username);
    }
}