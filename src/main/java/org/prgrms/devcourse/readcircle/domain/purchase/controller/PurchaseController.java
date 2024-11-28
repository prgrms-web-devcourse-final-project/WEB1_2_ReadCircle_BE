package org.prgrms.devcourse.readcircle.domain.purchase.controller;

import lombok.AllArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PurchaseDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.prgrms.devcourse.readcircle.domain.purchase.service.PurchaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseServiceImpl purchaseService;

    //매입 신청
    @PostMapping
    public ResponseEntity<PurchaseDTO> create(@RequestBody PurchaseDTO purchaseDTO){
        PurchaseDTO purchase = purchaseService.register(purchaseDTO);
        return ResponseEntity.ok(purchase);
    }

    //신청 상태로 매입 전체 조회 - 관리자
    @GetMapping
    public ResponseEntity<Page<PurchaseDTO>> readByStatus(
            @RequestParam(required=false) PurchaseStatus purchaseStatus
    ){
        Page<PurchaseDTO> purchaseDTOs = purchaseService.readByStatus(purchaseStatus);
        return ResponseEntity.ok(purchaseDTOs);
    }

    //닉네임으로 매입 전체 조회 - 사용자
    @GetMapping("/{nickname}")
    public ResponseEntity<Page<PurchaseDTO>> readByNickname(
            @PathVariable("nickname") String nickname
    ){
        Page<PurchaseDTO> purchaseDTOs = purchaseService.readByNickname(nickname);
        return ResponseEntity.ok(purchaseDTOs);
    }

    //가격 책정 - 관리자
    @PutMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDTO> updatePricing(
            @PathVariable("purchaseId") Long purchaseId,
            @RequestBody PricingDTO pricingDTO
        ){
        PurchaseDTO purchaseDTO = purchaseService.pricing(purchaseId, pricingDTO);
        return ResponseEntity.ok(purchaseDTO);
    }

    //매입 내역 삭제
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<?> deletePurchase(@PathVariable("purchaseId") Long purchaseId){
        purchaseService.delete(purchaseId);
        return ResponseEntity.ok("매입 내역 삭제에 성공하였습니다.");
    }
}
