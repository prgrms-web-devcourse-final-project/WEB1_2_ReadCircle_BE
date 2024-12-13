package org.prgrms.devcourse.readcircle.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.order.dto.request.OrderRequest;
import org.prgrms.devcourse.readcircle.domain.order.dto.response.OrderResponse;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.DeliveryStatus;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

//    // 1. 주문 생성
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse> createOrder(
//            @RequestBody OrderRequest orderRequest,  // 주문할 상품 목록
//            Authentication authentication
//    ) {
//        String userId = authentication.getName();
//        OrderResponse orderResponse = orderService.createOrder(userId, orderRequest);
//        return ResponseEntity.ok(ApiResponse.success(orderResponse));
//    }
//
//    // 2. 주문 취소 ( 취소된 주문 아카이브 처리 )
//    @PutMapping("/{orderId}/cancel")
//    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId, Authentication authentication) {
//        String userId = authentication.getName();
//        orderService.cancelOrder(orderId, userId);
//        return ResponseEntity.ok(ApiResponse.success(null));
//    }

    // 3. 주문 상세 조회 ( 취소된 주문은 볼 수 없음 )
    @GetMapping("/{orderId}/me")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable Long orderId, Authentication authentication) {
        String userId = authentication.getName();
        OrderResponse orderResponse = orderService.getOrderDetails(orderId, userId);
        return ResponseEntity.ok(ApiResponse.success(orderResponse));
    }

    // 4. 내 주문 목록 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserOrders(
            @RequestParam(defaultValue = "0") int page,   // 기본 페이지 번호는 0
            @RequestParam(defaultValue = "10") int size,    // 기본 페이지 크기는 10
            Authentication authentication
    ) {
        String userId = authentication.getName();
        Page<OrderResponse> orders = orderService.getUserOrders(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    // 5. 주문 상태 변경
    @PutMapping("/{orderId}/order-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 6. 배송 상태 변경
    @PutMapping("/{orderId}/delivery-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateDeliveryStatus(
            @PathVariable Long orderId,
            @RequestParam DeliveryStatus newStatus) {
        orderService.updateDeliveryStatus(orderId, newStatus);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 7. 모든 주문 목록 페이징 조회 (관리자)
    @GetMapping("/order-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<OrderResponse> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }


}
