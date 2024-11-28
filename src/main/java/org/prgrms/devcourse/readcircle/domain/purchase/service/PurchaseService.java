package org.prgrms.devcourse.readcircle.domain.purchase.service;

import org.prgrms.devcourse.readcircle.domain.purchase.dto.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PurchaseDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.springframework.data.domain.Page;

public interface PurchaseService {
    PurchaseDTO register(PurchaseDTO purchaseDTO);                      //매입 작성
    Page<PurchaseDTO> readByStatus(PurchaseStatus purchaseStatus);      //매입 전체 조회 - 관리자
    Page<PurchaseDTO> readByNickname(String nickname);   //매입 전체 조회 - 사용자
    PurchaseDTO pricing(Long purchaseId, PricingDTO pricingDTO);               // 가격 책정(책상태, 가격, 매입상태) - 관리자
    void delete(Long purchaseId);                       //매입 삭제
}
