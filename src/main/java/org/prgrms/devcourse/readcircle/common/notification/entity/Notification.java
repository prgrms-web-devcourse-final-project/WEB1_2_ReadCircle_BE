package org.prgrms.devcourse.readcircle.common.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prgrms.devcourse.readcircle.common.BaseTimeEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String toUserId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;
}
