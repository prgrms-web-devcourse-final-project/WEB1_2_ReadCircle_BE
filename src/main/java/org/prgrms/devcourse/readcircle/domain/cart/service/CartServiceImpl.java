package org.prgrms.devcourse.readcircle.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.book.service.BookService;
import org.prgrms.devcourse.readcircle.domain.cart.dto.CartItemDTO;
import org.prgrms.devcourse.readcircle.domain.cart.entity.Cart;
import org.prgrms.devcourse.readcircle.domain.cart.entity.CartItem;
import org.prgrms.devcourse.readcircle.domain.cart.exception.CartException;
import org.prgrms.devcourse.readcircle.domain.cart.respository.CartItemRepository;
import org.prgrms.devcourse.readcircle.domain.cart.respository.CartRepository;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final BookService bookService;

    @Override
    public void addCartItem(Long bookId, String userId){
        User user = userService.findUserByUserId(userId);
        Book book = bookService.getBookById(bookId);

        //Cart가 있는지, 없으면 새로운 Cart 생성
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });

        //Cart에 중복된 책 검사
        Optional<CartItem> existItem = Optional.ofNullable(cart.getCartItems())
                .orElse(Collections.emptyList())
                .stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst();

        //중복책 없으면 Cart에 추가
        if(existItem.isEmpty()){
            CartItem newCartItem = new CartItem(cart, book);
            cartItemRepository.save(newCartItem);
        }else {
            throw CartException.DUPLICATED_BOOK_EXCEPTION.getTaskException();
        }
    }

    @Override
    public List<CartItemDTO> findByUserId(String userId){
        Cart cart = cartRepository.findByUserIdWithCartItems(userId).orElseThrow(CartException.NOT_FOUND_CART_EXCEPTION::getTaskException);
        return cart.getCartItems()
                .stream().map(CartItemDTO::new)
                .toList();
    }

    @Override
    public void deleteCartItem(Long cartItemId, String userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(CartException.NOT_FOUND_CART_EXCEPTION::getTaskException);
        try{
            cartItemRepository.deleteById(cartItemId);
        }catch (Exception e){
            throw CartException.NOT_REMOVED_EXCEPTION.getTaskException();
        }
    }
}
