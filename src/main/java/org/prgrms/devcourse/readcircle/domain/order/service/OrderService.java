package org.prgrms.devcourse.readcircle.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.value.Address;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.book.exception.BookException;
import org.prgrms.devcourse.readcircle.domain.book.repository.BookRepository;
import org.prgrms.devcourse.readcircle.domain.order.dto.request.OrderRequest;
import org.prgrms.devcourse.readcircle.domain.order.dto.response.OrderResponse;
import org.prgrms.devcourse.readcircle.domain.order.entity.Delivery;
import org.prgrms.devcourse.readcircle.domain.order.entity.Order;
import org.prgrms.devcourse.readcircle.domain.order.entity.OrderArchive;
import org.prgrms.devcourse.readcircle.domain.order.entity.OrderItem;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.DeliveryStatus;
import org.prgrms.devcourse.readcircle.domain.order.entity.enums.OrderStatus;
import org.prgrms.devcourse.readcircle.domain.order.repository.OrderArchiveRepository;
import org.prgrms.devcourse.readcircle.domain.order.repository.OrderRepository;
import org.prgrms.devcourse.readcircle.domain.payment.entity.Payment;
import org.prgrms.devcourse.readcircle.domain.payment.entity.enums.PaymentStatus;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderArchiveRepository orderArchiveRepository;

    @Transactional  // 주문 생성
    public OrderResponse createOrder(String userId, OrderRequest orderRequest) {

        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NOT_FOUND::get);

        // Delivery 생성
        Delivery delivery = new Delivery();
        if(orderRequest.getAddress()!= null && !orderRequest.getAddress().isEmpty()) {
            delivery.changeRecipientAddress(new Address(orderRequest.getAddress()));
        } else {
            delivery.changeRecipientAddress(user.getAddress());
        }
        delivery.changeRecipientName(orderRequest.getRecipientName());
        delivery.changeDeliveryStatus(DeliveryStatus.PREPARING);

        // Order 객체 생성
        Order order = Order.builder()
                .delivery(delivery)
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .orderItems(new ArrayList<>())
                .totalPrice(0)
                .merchantUid(orderRequest.getMerchantUid())
                .build();

        // 주문 상품 리스트 생성
        int totalPrice = 0;
        for (Long bookId : orderRequest.getBookList()) {
            Book book = bookRepository.findById(bookId).orElseThrow(BookException.NOT_FOUND::get);

            if(!book.isForSale()) {
                throw new RuntimeException("Book is not for sale");
            }

            // OrderItem 생성
            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .price(book.getPrice())
                    .build();

            order.addOrderItem(orderItem);
            totalPrice += orderItem.getPrice();
        }

        order.changeTotalPrice(totalPrice);

        Payment.builder()
                .order(order)
                .impUid(orderRequest.getImpUid())
                .userId(userId)
                .paymentMethod(orderRequest.getPaymentMethod())
                .amount(totalPrice)
                .status(PaymentStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    @Transactional  // 주문 취소
    public void cancelOrder(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. ID: " + orderId));

        if(!order.getUser().getUserId().equals(userId)) { throw UserException.NOT_FOUND.get(); }

        if (order.getDelivery().getDeliveryStatus() == DeliveryStatus.DELIVERED) { throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다."); }

        // 주문 상태 확인
        if (order.getOrderStatus() == OrderStatus.CANCELED) { throw new IllegalStateException("이미 취소된 주문입니다."); }

        // 주문 상태 변경
        order.changeOrderStatus(OrderStatus.CANCELED);
        order.getPayment().changeStatus(PaymentStatus.CANCELLED);

        for (OrderItem orderItem : order.getOrderItems()) {
            Book book = orderItem.getBook();
            // 판매 중 상태로 복구
            book.changeIsForSale(true);
        }

        OrderArchive orderArchive = new OrderArchive(
                order.getId(),
                order.getUser().getId(),   // User의 ID만 저장
                order.getDelivery().getId(),  // Delivery의 ID만 저장
                order.getOrderStatus().name(),  // Enum 값을 String으로 저장
                order.getTotalPrice(),
                order.getPayment().getPaymentMethod(),
                order.getOrderDate(),
                LocalDateTime.now()  // 취소 일시 기록
        );

        orderArchiveRepository.save(orderArchive);  // 아카이브 테이블에 저장

        // order 테이블에서 데이터 삭제
        orderRepository.delete(order);
    }

    @Transactional  // 주문 내역 삭제
    public void deleteOrder(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. ID: " + orderId));

        if(!order.getUser().getUserId().equals(userId)) { throw UserException.NOT_FOUND.get(); }

        for (OrderItem orderItem : order.getOrderItems()) {
            Book book = orderItem.getBook();
            // 판매 중 상태로 복구
            book.changeIsForSale(true);
        }

        // order 테이블에서 데이터 삭제
        orderRepository.delete(order);
    }


    // 주문 상세 조회
    public OrderResponse getOrderDetails(Long orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. ID: " + orderId));

        if(!(order.getUser().getUserId().equals(userId))) { throw UserException.FORBIDDEN_ACCESS.get(); }
        return OrderResponse.from(order);
    }

    // 내 주문 목록 조회
    public Page<OrderResponse> getUserOrders(String userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NOT_FOUND::get);

        Page<Order> orders = orderRepository.findAllByUserId(user.getUserId(), pageable);

        return orders.map(OrderResponse::from);
    }

    @Transactional // 주문 상태 변경
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. ID: " + orderId));

        order.changeOrderStatus(newStatus);
    }

    @Transactional // 배송 상태 변경
    public void updateDeliveryStatus(Long orderId, DeliveryStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. ID: " + orderId));

        Delivery delivery = order.getDelivery();
        delivery.changeDeliveryStatus(newStatus);
    }

    // 모든 주문 목록 조회(관리자)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(OrderResponse::from);
    }


}
