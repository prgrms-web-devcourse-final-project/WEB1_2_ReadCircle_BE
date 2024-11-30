package org.prgrms.devcourse.readcircle.domain.order.repository;

import org.prgrms.devcourse.readcircle.domain.order.entity.OrderArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderArchiveRepository extends JpaRepository<OrderArchive, Long> {
}
