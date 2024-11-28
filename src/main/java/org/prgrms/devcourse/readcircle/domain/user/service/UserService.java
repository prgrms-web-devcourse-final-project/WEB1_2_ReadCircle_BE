package org.prgrms.devcourse.readcircle.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.upload.ImageRepository;
import org.prgrms.devcourse.readcircle.domain.user.dto.UserDTO;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserSignUpRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserUpdateByAdminRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserUpdateRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.response.UserInfoByAdminResponse;
import org.prgrms.devcourse.readcircle.domain.user.dto.response.UserInfoResponse;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void signUp(final UserSignUpRequest request, final MultipartFile profileImage) {
        // 중복 체크
        if (userRepository.existsByUserId(request.getUserId())) { throw UserException.DUPLICATE_ID.get(); }
        if (userRepository.existsByEmail(request.getEmail())) { throw UserException.DUPLICATE_EMAIL.get(); }
        if (userRepository.existsByNickname(request.getNickname())) { throw UserException.DUPLICATE_NICKNAME.get(); }

        // 프로필 이미지 처리
        String imageUrl = (profileImage != null && !profileImage.isEmpty())
                ? imageRepository.upload(profileImage)
                : "defaultImage.png";

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        userRepository.save(request.toEntity(encodedPassword, imageUrl));
    }

    @Transactional
    public void updateUser(String userId, UserUpdateRequest request) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);

        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) { throw UserException.DUPLICATE_EMAIL.get(); }

        if(request.getNickname() != null) { foundUser.changeNickname(request.getNickname()); }
        if(request.getEmail() != null) { foundUser.changeEmail(request.getEmail()); }
        if(request.getAddress() != null) { foundUser.changeAddress(request.getAddress()); }
    }

    public UserDTO findByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);
        return new UserDTO(user);
    }

    @Transactional  // 비밀번호 변경
    public void updatePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);

        // 현재 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw UserException.NOT_MATCHED_PASSWORD.get();
        }

        // 새 비밀번호 변경
        user.changePassword(newPassword, passwordEncoder);

    }

    @Transactional  // 프로필 사진 변경
    public void updateProfileImage(String userId, MultipartFile image) {
        // 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);

        // 이미지가 제출되지 않았다면, 기존 이미지를 유지
        if (image != null && !image.isEmpty()) {
            // 파일 업로드
            String imageUrl = imageRepository.upload(image);
            // 이미지 URL을 사용자 프로필에 업데이트
            user.changeProfileImageUrl(imageUrl);
        }
    }

    @Transactional        // 회원 탈퇴
    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);

        userRepository.delete(user);
    }

    public UserInfoResponse getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);
        return new UserInfoResponse(user, uploadPath);
    }

    public Page<UserInfoByAdminResponse> getAllUsersByAdmin(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserInfoByAdminResponse::new);
    }

    public UserInfoByAdminResponse getUserInfoByAdmin(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);
        return new UserInfoByAdminResponse(user);
    }

    @Transactional
    public void updateUserByAdmin(String userId, UserUpdateByAdminRequest request) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);

        // 요청바디에 있는 필드만 수정
        if(request.getEmail() != null) { foundUser.changeEmail(request.getEmail()); }
        if(request.getRole() != null) { foundUser.changeRole(request.getRole()); }
        if(request.getPassword() != null) { foundUser.changePassword(request.getPassword(), passwordEncoder); }
        if(request.getNickname() != null) { foundUser.changeNickname(request.getNickname()); }
        if(request.getAddress() != null) { foundUser.changeAddress(request.getAddress()); }
    }


    //UserId로 찾고 User를 반환
    public User findUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserException.NOT_FOUND::get);
        return user;
    }
}

