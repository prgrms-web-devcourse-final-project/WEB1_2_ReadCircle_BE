package org.prgrms.devcourse.readcircle.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.order.dto.request.OrderRequest;
import org.prgrms.devcourse.readcircle.domain.order.dto.response.OrderResponse;
import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.order.repository.OrderRepository;
import org.prgrms.devcourse.readcircle.domain.order.service.OrderService;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PortOneWebhookRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PaymentRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.response.PaymentInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PortOneClient portOneClient;
    private final OrderService orderService;

    // 1. 주문 생성 및 merchant_uid 반환
    public String createOrder(PaymentRequest request, String userId) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAddress(request.getAddress());
        orderRequest.setBookList(request.getBookList());
        orderRequest.setPaymentMethod(request.getPaymentMethod());
        orderRequest.setRecipientName(request.getRecipientName());
        OrderResponse order = orderService.createOrder(userId, orderRequest);

        return order.getMerchantUid();
    }

    // 2. 웹훅 요청 처리 및 결제 상태 검증
    public void verifyAndProcessPayment(PortOneWebhookRequest webhookRequest) {
        String impUid = webhookRequest.getImpUid();
        String merchantUid = webhookRequest.getMerchantUid();

        // 포트원 서버에 결제 정보 검증 요청
        PaymentInfo paymentInfo = portOneClient.getPaymentInfo(impUid);

        // 결제 검증: 금액 확인
        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (order.getTotalPrice() != paymentInfo.getAmount()) {
            throw new IllegalStateException("결제 금액 불일치");
        }

        // 결제 상태 업데이트
        if ("paid".equals(paymentInfo.getStatus())) {
            order.changeOrderStatus(OrderStatus.PAID);
        } else {
            order.changeOrderStatus(OrderStatus.FAILED);
        }
        orderRepository.save(order);
    }
}
