package org.prgrms.devcourse.readcircle.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.auth.dto.LoginDTO;
import org.prgrms.devcourse.readcircle.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Map<String, String> tokens = authService.login(loginDTO, response);
        return ResponseEntity.ok(ApiResponse.success(tokens));
    }



}
