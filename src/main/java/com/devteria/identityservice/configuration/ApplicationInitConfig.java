package com.devteria.identityservice.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devteria.identityservice.constant.PredefinedRole;
import com.devteria.identityservice.entity.Role;
import com.devteria.identityservice.entity.UsersEntity;
import com.devteria.identityservice.repository.RoleRepository;
import com.devteria.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    static final String ADMIN_USER_NAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            log.info("🔧 Initializing application...");

            // Kiểm tra xem user admin đã tồn tại chưa
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {

                // Tạo role USER nếu chưa có
                roleRepository.findById(PredefinedRole.USER_ROLE).orElseGet(() ->
                        roleRepository.save(Role.builder()
                                .name(PredefinedRole.USER_ROLE)
                                .description("User role")
                                .build())
                );

                // Tạo role ADMIN nếu chưa có
                Role adminRole = roleRepository.findById(PredefinedRole.ADMIN_ROLE).orElseGet(() ->
                        roleRepository.save(Role.builder()
                                .name(PredefinedRole.ADMIN_ROLE)
                                .description("Admin role")
                                .build())
                );

                // Gán role ADMIN cho user admin mặc định
                var roles = new HashSet<Role>();
                roles.add(adminRole);

                UsersEntity admin = UsersEntity.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(admin);
                log.warn("⚠️  Admin user has been created with default password: '{}'. Please change it.", ADMIN_PASSWORD);
            }

            log.info("✅ Application initialization completed.");
        };
    }
}