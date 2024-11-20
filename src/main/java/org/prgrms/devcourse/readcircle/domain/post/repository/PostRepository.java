package org.prgrms.devcourse.readcircle.domain.post.repository;

import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(" SELECT p FROM Post p WHERE p.tradeStatus = false ")
    Page<PostDTO> getAll(Pageable pageable);

    @Query(" SELECT p FROM Post p WHERE p.user.userId = :userId ")
    Page<PostDTO> getPostByUserId(@Param("userId") String userId, Pageable pageable);

    @Query(" SELECT p FROM Post p WHERE p.title LIKE %:keyword% ")
    Page<PostDTO> getPostByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(" SELECT p FROM Post p WHERE p.bookCategory = :bookCategory ")
    Page<PostDTO> getPostByCategory(@Param("bookCategory")BookCategory bookCategory, Pageable pageable);
}