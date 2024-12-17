package org.prgrms.devcourse.readcircle.domain.wish.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.wish.dto.WishDTO;
import org.prgrms.devcourse.readcircle.domain.wish.service.WishServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishController {
    private final WishServiceImpl wishService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse> addPostWish(
            @RequestParam("id") Long postId,
            Authentication authentication
    ){
        wishService.addPostWish(postId, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse> addBookWish(
            @RequestParam("id") Long bookId,
            Authentication authentication
    ){
        wishService.addBookWish(bookId, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getWishList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Authentication authentication
    ){
        String userId = authentication.getName();
        Page<WishDTO> wishList = wishService.wishList(userId, 0, 10);
        return ResponseEntity.ok(ApiResponse.success(wishList));
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteWish(
            @RequestParam("wishId") Long wishId,
            Authentication authentication
    ){
        wishService.deleteWish(wishId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}