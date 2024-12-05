package org.prgrms.devcourse.readcircle.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PaymentRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PortOneWebhookRequest;
import org.prgrms.devcourse.readcircle.domain.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {
    private final PaymentService paymentService;

    // 결제 요청 생성
    @PostMapping("/prepare")
    public ResponseEntity<String> preparePayment(@RequestBody PaymentRequest paymentRequest, Authentication authentication) {
        String userId = authentication.getName();
        String merchantUid = paymentService.createOrder(paymentRequest, userId);
        return ResponseEntity.ok(merchantUid);
    }

    // 웹훅 처리 (포트원 서버에서 호출)
    @PostMapping("/webhook")
    @CrossOrigin(origins = "*") // 모든 요청 허용
    public ResponseEntity<?> handleWebhook(@RequestBody PortOneWebhookRequest webhookRequest) {
        log.info(webhookRequest.getImpUid());
        log.info(webhookRequest.getMerchantUid());
        paymentService.verifyAndProcessPayment(webhookRequest);
        return ResponseEntity.ok("success");
    }
}
