package org.prgrms.devcourse.readcircle.domain.wish.service;

import org.prgrms.devcourse.readcircle.domain.wish.dto.WishDTO;
import org.springframework.data.domain.Page;

public interface WishService {

    void addPostWish(Long postId, String userId);
    void addBookWish(Long bookId, String userId);
    Page<WishDTO> wishList(String userId, int page, int size);
    void deleteWish(Long wishId);
}
