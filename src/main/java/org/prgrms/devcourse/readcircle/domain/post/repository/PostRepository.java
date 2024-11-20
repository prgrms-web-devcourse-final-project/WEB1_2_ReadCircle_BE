package org.prgrms.devcourse.readcircle.domain.post.repository;

import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(" SELECT p FROM Post p WHERE p.user.userId = :userId ORDER BY p.tradeStatus ASC ")
    Optional<List<Post>> getPostByUserId(@Param("userId") String userId);

    @Query(" SELECT p FROM Post p WHERE p.title LIKE %:keyword% ")
    Optional<List<Post>> getPostByKeyword(@Param("keyword") String keyword);

    @Query(" SELECT p FROM Post p WHERE p.bookCategory = :bookCategory ORDER BY p.tradeStatus ASC ")
    Optional<List<Post>> getPostByCategory(@Param("bookCategory")BookCategory bookCategory);
}
