package org.prgrms.devcourse.readcircle.domain.payment.repository;

import org.prgrms.devcourse.readcircle.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
