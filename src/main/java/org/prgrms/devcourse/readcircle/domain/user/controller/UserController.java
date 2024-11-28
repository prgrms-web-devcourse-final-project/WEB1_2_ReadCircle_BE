package org.prgrms.devcourse.readcircle.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.user.dto.UserDTO;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.ChangePasswordRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserSignUpRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserUpdateByAdminRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserUpdateRequest;
import org.prgrms.devcourse.readcircle.domain.user.dto.response.UserInfoByAdminResponse;
import org.prgrms.devcourse.readcircle.domain.user.dto.response.UserInfoResponse;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<ApiResponse> signUp(@RequestPart("request") @Valid final UserSignUpRequest request,
                                              @RequestPart(value = "image", required = false) final MultipartFile profileImage) {
        userService.signUp(request, profileImage);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/me")  // 내 정보 조회
    public ResponseEntity<ApiResponse> getCurrentUser(Authentication authentication) {
        String userId = authentication.getName(); // 인증된 사용자 ID 가져오기
        UserDTO userDTO = userService.findByUserId(userId); // 사용자 정보 조회
        return ResponseEntity.ok(ApiResponse.success(userDTO)); // 사용자 정보를 포함한 ApiResponse 반환
    }

    @GetMapping("{userId}")  // 다른 회원 정보 조회
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable("userId") final String userId) {
        UserInfoResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }

    @PatchMapping("/me")   // 내 정보 수정
    public ResponseEntity<ApiResponse> updateCurrentUser(@RequestBody @Valid UserUpdateRequest request,
                                                                             Authentication authentication) {
        String userId = authentication.getName();
        userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/me/password") // 비밀번호 변경
    public ResponseEntity<ApiResponse> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        userService.updatePassword(userId, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping(value = "/me/profileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   // 프로필 사진 수정
    public ResponseEntity<ApiResponse> modifyProfileImage(
            @RequestPart(value = "image", required = false) final MultipartFile image,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        String imageUrl = userService.updateProfileImage(userId, image);
        return ResponseEntity.ok(ApiResponse.success(imageUrl));
    }

    @DeleteMapping("/me")  // 회원 탈퇴
    public ResponseEntity<ApiResponse> deleteCurrentUser(Authentication authentication) {
        String userId = authentication.getName();
        userService.deleteUser(userId);  // 사용자 삭제 서비스 호출
        return ResponseEntity.ok(ApiResponse.success(null));  // 성공 메시지 반환
    }

    //------------------------- ADMIN 권한 기능 -----------------------------------------

    @GetMapping("/admin/user-list")  // 회원 목록 조회
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<UserInfoByAdminResponse> users = userService.getAllUsersByAdmin(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/admin/{userId}")   // 특정 사용자 정보 조회
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserInfoByAdminResponse>> getUserInfoByAdmin(@PathVariable("userId") String userId) {
        UserInfoByAdminResponse userInfo = userService.getUserInfoByAdmin(userId);
        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }


    @PatchMapping("/admin/{userId}")  // 회원 정보 수정
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateUserInfoByAdmin(
            @PathVariable("userId") String userId,
            @RequestBody final UserUpdateByAdminRequest request
    ) {
        userService.updateUserByAdmin(userId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping(value = "/admin/profileImage/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   // 회원 프로필 사진 수정
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> modifyProfileImage(
            @PathVariable("userId") String userId,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        String imageUrl = userService.updateProfileImage(userId, image);
        return ResponseEntity.ok(ApiResponse.success(imageUrl));
    }

    @DeleteMapping("/admin/{userId}")  // 회원 삭제
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUserByAdmin(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
