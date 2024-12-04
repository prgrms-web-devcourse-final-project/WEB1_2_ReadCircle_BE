package org.prgrms.devcourse.readcircle.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.cart.dto.CartItemDTO;
import org.prgrms.devcourse.readcircle.domain.cart.service.CartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartServiceImpl cartService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<ApiResponse> addCartItem(
            @PathVariable("bookId") Long bookId,
            Authentication authentication
    ){
        String userId = authentication.getName();
        cartService.addCartItem(bookId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUsersCart(Authentication authentication){
        String userId = authentication.getName();
        List<CartItemDTO> cartItems = cartService.findByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(cartItems));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCartItem(
            @RequestParam("cartItemId") Long cartItemId,
            Authentication authentication
    ){
        cartService.deleteCartItem(cartItemId, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
