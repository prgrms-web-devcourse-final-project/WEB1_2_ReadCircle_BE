package org.prgrms.devcourse.readcircle.domain.comment.service;

import org.prgrms.devcourse.readcircle.domain.comment.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO register(CommentDTO commentDTO);         //댓글 작성
    List<CommentDTO> readAllByPostId(Long postId);   //게시글에 대한 댓글 조회
    List<CommentDTO> readAllByUserId(String userId);
    void delete(Long commentId);                        //댓글 삭제
}
