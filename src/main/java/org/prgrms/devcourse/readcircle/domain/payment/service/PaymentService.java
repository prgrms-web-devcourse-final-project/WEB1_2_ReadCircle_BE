package org.prgrms.devcourse.readcircle.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.order.dto.request.OrderRequest;
import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.order.repository.OrderRepository;
import org.prgrms.devcourse.readcircle.domain.order.service.OrderService;
import org.prgrms.devcourse.readcircle.domain.payment.dto.request.PaymentRequest;
import org.prgrms.devcourse.readcircle.domain.payment.dto.response.PaymentInfo;
import org.prgrms.devcourse.readcircle.domain.payment.entity.enums.PaymentStatus;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PortOneClient portOneClient;
    private final OrderService orderService;

    // 1. 주문 생성 및 merchant_uid 반환
    public void createOrder(PaymentRequest request, String userId) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAddress(request.getAddress());
        orderRequest.setBookList(request.getBookList());
        orderRequest.setPaymentMethod(request.getPaymentMethod());
        orderRequest.setRecipientName(request.getRecipientName());
        orderRequest.setMerchantUid(request.getMerchantUid());
        orderRequest.setImpUid(request.getImpUid());
        orderService.createOrder(userId, orderRequest);

    }

    // 2.  결제 검증 및 결과 반환
    @Transactional
    public PaymentInfo verifyAndProcessPayment(String impUid, String merchantUid) {

        // 포트원 서버에 결제 정보 검증 요청
        PaymentInfo paymentInfo = portOneClient.getPaymentInfo(impUid);

        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        // 책 가격과 결제 가격이 일치하는지 확인
        if (order.getTotalPrice() != paymentInfo.getAmount()) {
            throw new IllegalStateException("price not match");
        }

        // 결제 상태 업데이트
        if ("paid".equals(paymentInfo.getPaymentStatus())) {
            order.changeOrderStatus(OrderStatus.PAID);
            order.getPayment().changeStatus(PaymentStatus.PAID);
        } else {
            order.changeOrderStatus(OrderStatus.FAILED);
            order.getPayment().changeStatus(PaymentStatus.FAILED);
            deleteOrder(merchantUid, order.getUser().getUserId());
        }

        return paymentInfo;
    }

    @Transactional
    public void cancelOrderPayment(String merchantUid, String reason, String userId) {
        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        if(!userId.equals(order.getUser().getUserId())) {
            throw UserException.FORBIDDEN_ACCESS.get();
        }

        if (!OrderStatus.PAID.equals(order.getOrderStatus())) {
            throw new IllegalStateException("Only paid orders can be canceled.");
        }

        // 포트원 API를 통해 결제 취소 요청
        portOneClient.cancelPayment(order.getPayment().getImpUid(), reason);

        // 주문 취소
        orderService.cancelOrder(order.getId(), userId);

        // 주문 상태 변경
        order.changeOrderStatus(OrderStatus.CANCELED);
        order.getPayment().changeStatus(PaymentStatus.CANCELLED);

    }

    public void deleteOrder(String merchantUid, String userId) {
        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        Long orderId = order.getId();

        orderService.deleteOrder(orderId, userId);
    }
}
