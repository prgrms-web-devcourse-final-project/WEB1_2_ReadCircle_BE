package org.prgrms.devcourse.readcircle.domain.wish.repository;

import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.seller.entity.Seller;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.wish.dto.WishDTO;
import org.prgrms.devcourse.readcircle.domain.wish.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w FROM Wish w JOIN FETCH w.user u WHERE u.userId = :userId")
    Page<Wish> getWishListByUserId(String userId, Pageable pageable);


    @Query("SELECT w FROM Wish w JOIN FETCH w.user u WHERE u.userId = :userId")
    List<Wish> getWishs(String userId);


    @Query("SELECT w FROM Wish w WHERE w.post = :post AND w.user = :user")
    Optional<Wish> findWishPost(Post post, User user);

    @Query("SELECT w FROM Wish w WHERE w.book = :book AND w.user = :user")
    Optional<Wish> findWishBook(Book book, User user);
}
