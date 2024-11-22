package org.prgrms.devcourse.readcircle.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.comment.entity.Comment;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private Long postId;
    private String userId;
    private LocalDateTime commentCreatedAt;

    public Comment toEntity(){
        User user = User.builder()
                .userId(userId)
                .build();

        return Comment.builder()
                .commentId(commentId)
                .commentContent(commentContent)
                .postId(postId)
                .user(user)
                .commentCreatedAt(commentCreatedAt)
                .build();
    }

    public CommentDTO(Comment comment){
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getCommentContent();
        this.postId = comment.getPostId();
        this.userId = comment.getUser().getUserId();
        this.commentCreatedAt = comment.getCommentCreatedAt();
    }
}
