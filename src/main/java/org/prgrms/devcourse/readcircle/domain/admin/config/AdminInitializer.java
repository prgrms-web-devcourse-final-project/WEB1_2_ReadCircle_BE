package org.prgrms.devcourse.readcircle.domain.admin.config;

import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.entity.enums.Role;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 최초 관리자 계정 생성
@Component
@Log4j2
public class AdminInitializer implements CommandLineRunner {

    @Value("${file.local.upload.path}")
    private String uploadPath;

    @Value("${admin.userId}")
    private String adminUserId;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUserId(adminUserId).isEmpty()) {
            User admin = User.builder()
                    .userId(adminUserId)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .nickname("root_admin")
                    .role(Role.ADMIN)
                    .profileImageUrl("defaultImage.png")
                    .build();
            userRepository.save(admin);
            log.info("환경변수를 통해 초기 관리자 계정 생성 완료");
        }
    }
}
