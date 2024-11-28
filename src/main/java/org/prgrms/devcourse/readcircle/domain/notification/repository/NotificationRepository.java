package org.prgrms.devcourse.readcircle.domain.notification.repository;

import org.prgrms.devcourse.readcircle.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
