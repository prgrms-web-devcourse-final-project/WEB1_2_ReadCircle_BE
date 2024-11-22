package org.prgrms.devcourse.readcircle.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.comment.dto.CommentDTO;
import org.prgrms.devcourse.readcircle.domain.comment.entity.Comment;
import org.prgrms.devcourse.readcircle.domain.comment.exception.CommentException;
import org.prgrms.devcourse.readcircle.domain.comment.repository.CommentRepository;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.exception.UserException;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserFindRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserFindRepository userFindRepository;

    //댓글 생성
    @Override
    public CommentDTO register(CommentDTO commentDTO){
        try{
            User user = userFindRepository.findByUserId(commentDTO.getUserId()).orElseThrow(UserException.NOT_FOUND::get);
            Comment comment = commentDTO.toEntity();
            comment.setUser(user);
            commentRepository.save(comment);
            return new CommentDTO(comment);
        } catch(Exception e){
            throw CommentException.NOT_REGISTERED_EXCEPTION.getTaskException();
        }
    }

    //게시글에 대한 댓글 조회
    @Override
    public List<CommentDTO> readAllByPostId(Long postId){
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        return commentList.stream().map(CommentDTO::new).toList();
    }

    //유저 닉네임으로 댓글 조회 (마이페이지)
    @Override
    public List<CommentDTO> readAllByNickname(String nickname){
        List<Comment> commentList = commentRepository.findAllByNickname(nickname);
        return commentList.stream().map(CommentDTO::new).toList();
    }

    //댓글 삭제
    @Override
    public void delete(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            commentRepository.delete(comment);
        } catch (Exception e){
            throw CommentException.NOT_REMOVED_EXCEPTION.getTaskException();
        }
    }
}
