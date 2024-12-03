package org.prgrms.devcourse.readcircle.domain.seller.service;

import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.domain.book.dto.response.BookResponse;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.SellerBookRequest;
import org.prgrms.devcourse.readcircle.domain.seller.dto.response.SellerDTO;
import org.springframework.data.domain.Page;

public interface SellerService {
    SellerDTO register(SellerBookRequest request, String userId);
    Page<SellerDTO> findByUserId(String userId, BookProcess process, int page, int size);
    Page<SellerDTO> findByAdmin(BookProcess process, int page, int size);
    BookResponse pricing(Long sellerId, PricingDTO pricingDTO);
    void delete(Long sellerId);
}
