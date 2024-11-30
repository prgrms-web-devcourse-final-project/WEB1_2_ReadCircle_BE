package org.prgrms.devcourse.readcircle.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.order.dto.request.OrderRequest;
import org.prgrms.devcourse.readcircle.domain.order.dto.response.OrderResponse;
import org.prgrms.devcourse.readcircle.domain.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // 1. 주문 생성
    @PostMapping("create")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestBody OrderRequest orderRequest,  // 주문할 상품 목록
            Authentication authentication
    ) {
        String userId = authentication.getName();
        OrderResponse orderResponse = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(ApiResponse.success(orderResponse));
    }

    // 2. 주문 취소 ( 취소된 주문 아카이브 처리 )
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId, Authentication authentication) {
        String userId = authentication.getName();
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 3. 주문 상세 조회 ( 취소된 주문은 볼 수 없음 )
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(ApiResponse.success(orderResponse));
    }

    // 4. 내 주문 목록 조회
    @GetMapping("/me/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(
            @RequestParam(defaultValue = "0") int page,   // 기본 페이지 번호는 0
            @RequestParam(defaultValue = "10") int size,    // 기본 페이지 크기는 10
            Authentication authentication
    ) {
        String userId = authentication.getName();
        Page<OrderResponse> orders = orderService.getUserOrders(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(orders));
    }




}
