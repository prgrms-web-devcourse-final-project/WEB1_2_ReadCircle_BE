package org.prgrms.devcourse.readcircle.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="comment")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    public void setUser(User user){ this.user = user; }
}
