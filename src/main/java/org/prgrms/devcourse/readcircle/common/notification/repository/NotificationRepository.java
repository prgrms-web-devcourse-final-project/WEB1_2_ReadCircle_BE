package org.prgrms.devcourse.readcircle.common.notification.repository;

import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.toUserId = :toUserId")
    List<Notification> findByToUserId(String toUserId);

    @Modifying
    @Query(value = "DELETE FROM Notification WHERE created_at < now() - interval 1 DAY; ", nativeQuery = true)
    void deleteNotifications();
}
