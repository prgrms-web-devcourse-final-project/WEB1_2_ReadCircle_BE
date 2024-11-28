package org.prgrms.devcourse.readcircle.domain.notification.entity;

public enum NotificationType {
    PURCHASE_RESPONSE_WAITING,      //매입 신청
    PURCHASE_PRICE_SET,             //매입 가격 결정
    POST_NEW_COMMENT,       //내 게시글에 새 댓글
    PAYMENT_SUCCESS,        //결제 성공
    ORDER_BOOK              //사용자가 책 구매
}