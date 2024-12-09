package org.prgrms.devcourse.readcircle.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.common.notification.service.NotificationService;
import org.prgrms.devcourse.readcircle.domain.comment.dto.CommentDTO;
import org.prgrms.devcourse.readcircle.domain.comment.entity.Comment;
import org.prgrms.devcourse.readcircle.domain.comment.exception.CommentException;
import org.prgrms.devcourse.readcircle.domain.comment.repository.CommentRepository;
import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.service.PostServiceImpl;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostServiceImpl postService;
    private final NotificationService notificationService;

    //댓글 생성
    @Override
    public CommentDTO register(CommentDTO commentDTO, String userId){
        User user = userService.findUserByUserId(userId);
        PostDTO postDTO = postService.read(commentDTO.getPostId());
        try{

            Comment comment = commentDTO.toEntity();
            comment.setUser(user);
            commentRepository.save(comment);

            //SSE 알림 - message와 tpye, 알림 대상자 설정 필요
            String message = "마켓 게시글 "+postDTO.getTitle()+"에 새로운 댓글이 달렸어요!";
            NotificationType type = NotificationType.NEW_COMMENT;
            notificationService.saveNotification(postDTO.getUserId(), message, type);

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
    public List<CommentDTO> readAllByUserId(String userId){
        List<Comment> commentList = commentRepository.findAllByUserId(userId);
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
