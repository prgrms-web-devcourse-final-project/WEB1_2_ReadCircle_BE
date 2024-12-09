package org.prgrms.devcourse.readcircle.domain.seller.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.book.dto.response.BookResponse;
import org.prgrms.devcourse.readcircle.domain.book.service.BookService;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.SellerBookRequest;
import org.prgrms.devcourse.readcircle.domain.seller.dto.response.SellerDTO;
import org.prgrms.devcourse.readcircle.domain.seller.service.SellerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/sale")
public class SellerController {
    private final SellerServiceImpl sellerService;
    private final BookService bookService;

    //seller 및 book 생성
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody SellerBookRequest sellerBookRequest,
            Authentication authentication
    ){
        String userId = authentication.getName();
        sellerService.register(sellerBookRequest, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //seller 전체 조회 - 사용자
    @GetMapping
    public ResponseEntity<ApiResponse> ReadAll(
            @RequestParam(required=false, defaultValue = "WAITING") BookProcess process,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Authentication authentication
    ){
        String userId = authentication.getName();
        Page<SellerDTO> sellerDTO = sellerService.findByUserId(userId, process, page, size);
        return ResponseEntity.ok(ApiResponse.success(sellerDTO));
    }

    //seller 전체 조회 - 관리자
    @GetMapping("/management")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> ReadAllByAdmin(
            @RequestParam(required=false, defaultValue = "WAITING") BookProcess process,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        Page<SellerDTO> sellerDTO = sellerService.findByAdmin(process, page, size);
        return ResponseEntity.ok(ApiResponse.success(sellerDTO));
    }

    //seller 매입가 결정 - 관리자
    @PutMapping("/management/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updatePricing(
            @PathVariable("sellerId") Long sellerId,
            @RequestBody PricingDTO pricingDTO
    ){
        BookResponse book = sellerService.pricing(sellerId, pricingDTO);
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    //seller 삭제 - 관리자
    @DeleteMapping("/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteSeller(
            @PathVariable("sellerId") Long sellerId
    ){
        sellerService.delete(sellerId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
