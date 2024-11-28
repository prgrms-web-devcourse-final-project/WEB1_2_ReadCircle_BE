package org.prgrms.devcourse.readcircle.domain.purchase.repository;

import org.prgrms.devcourse.readcircle.domain.purchase.entity.Purchase;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(" SELECT p FROM Purchase p WHERE p.purchaseStatus = :purchaseStatus ")
    Page<Purchase> findByStatus(@Param("purchaseStatus")PurchaseStatus purchaseStatus, Pageable pageable);

    @Query(" SELECT p FROM Purchase p WHERE p.user.nickname = :nickname ORDER BY p.purchaseStatus ")
    Page<Purchase> findByNickname(@Param("nickname")String nickname, Pageable pageable);
}
