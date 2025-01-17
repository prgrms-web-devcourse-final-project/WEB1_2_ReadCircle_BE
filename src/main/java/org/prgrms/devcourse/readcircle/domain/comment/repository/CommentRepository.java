package org.prgrms.devcourse.readcircle.domain.comment.repository;

import org.prgrms.devcourse.readcircle.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(" SELECT c FROM Comment c JOIN FETCH c.user WHERE c.postId = :postId ")
    List<Comment> findAllByPostId(@Param("postId") Long postId);

    @Query(" SELECT c FROM Comment c JOIN FETCH c.user WHERE c.user.userId = :userId ")
    List<Comment> findAllByUserId(@Param("userId") String userId);
}
