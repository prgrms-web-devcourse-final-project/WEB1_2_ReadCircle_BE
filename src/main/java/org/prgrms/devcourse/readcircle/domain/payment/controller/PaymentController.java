package org.prgrms.devcourse.readcircle.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.CancelPaymentRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PaymentRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.response.PaymentInfo;
import org.prgrms.devcourse.readcircle.domain.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/process")  // 주문 생성 및 결제 검증 및 결과확인
    public ResponseEntity<ApiResponse> processPayment(@RequestBody PaymentRequest paymentRequest, Authentication authentication) {
        String userId = authentication.getName();
        // 1. 주문 생성
        paymentService.createOrder(paymentRequest, userId);

        // 2. 포트원 결제 정보 검증 및 결제 정보 반환
        PaymentInfo paymentInfo = paymentService.verifyAndProcessPayment(paymentRequest.getImpUid(), paymentRequest.getMerchantUid());

        // 3. 결과 반환
        return ResponseEntity.ok(ApiResponse.success(paymentInfo));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelPayment(@RequestBody CancelPaymentRequest cancelRequest, Authentication authentication) {
        String userId = authentication.getName();

        // 결제 취소 처리
        paymentService.cancelOrderPayment(cancelRequest.getMerchantUid(), cancelRequest.getReason(), userId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
