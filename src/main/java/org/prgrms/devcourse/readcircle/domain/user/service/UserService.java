package org.prgrms.devcourse.readcircle.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.upload.ImageRepository;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserSignUpRequest;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    @Value("${file.local.upload.path}")
    private String uploadPath;

    //회원 가입
    @Transactional
    public void signUp(final UserSignUpRequest userSignUpRequest, final MultipartFile profileImage) {
        validateDuplicateUser(userSignUpRequest); // 중복 체크

        String imageUrl = processProfileImage(profileImage); // 프로필 이미지 처리

        String encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword());

        userRepository.save(userSignUpRequest.toEntity(encodedPassword, imageUrl));
    }



    // 중복 체크 메서드
    private void validateDuplicateUser(final UserSignUpRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw UserException.DUPLICATE_ID.get();
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserException.DUPLICATE_EMAIL.get();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw UserException.DUPLICATE_NICKNAME.get();
        }
    }

    // 프로필 이미지 처리 메서드
    private String processProfileImage(final MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return uploadPath + "/defaultImage.png";
        }
        return imageRepository.upload(profileImage);
    }



}
