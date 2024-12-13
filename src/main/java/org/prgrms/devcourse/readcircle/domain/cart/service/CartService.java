package org.prgrms.devcourse.readcircle.domain.cart.service;

import org.prgrms.devcourse.readcircle.domain.cart.dto.CartItemDTO;

import java.util.List;

public interface CartService{
    void addCartItem(Long bookId, String userId);       //카트 아이템 추가
    List<CartItemDTO> findByUserId(String userId);      //사용자 카트 조회
    void deleteCartItem(Long cartItemId, String userId);    //카트 아이템 삭제

    void deleteCart(String usrId);      //카트 삭제
}
