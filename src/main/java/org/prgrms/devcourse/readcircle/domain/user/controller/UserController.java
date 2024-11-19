package org.prgrms.devcourse.readcircle.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.user.dto.request.UserSignUpRequest;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<ApiResponse> signUp(@RequestPart("request") @Valid final UserSignUpRequest request, @RequestPart(value = "image", required = false) final MultipartFile profileImage) {
        userService.signUp(request, profileImage);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
