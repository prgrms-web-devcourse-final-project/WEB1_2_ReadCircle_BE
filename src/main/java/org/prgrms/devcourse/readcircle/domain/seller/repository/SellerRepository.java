package org.prgrms.devcourse.readcircle.domain.seller.repository;

import jakarta.persistence.Entity;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.domain.seller.entity.Seller;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("SELECT s FROM Seller s JOIN FETCH s.book b WHERE s.userId = :userId AND b.process = :process")
    Page<Seller> getSellerByUserId(String userId, @Param("process") BookProcess process, Pageable pageable);

    @Query("SELECT s FROM Seller s JOIN FETCH s.book b WHERE b.process = :process")
    Page<Seller> getSellerByAdmin(@Param("process") BookProcess process, Pageable pageable);
}
