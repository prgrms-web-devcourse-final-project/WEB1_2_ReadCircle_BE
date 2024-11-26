package org.prgrms.devcourse.readcircle.domain.purchase.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.util.PagingUtil;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PurchaseDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.Purchase;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.prgrms.devcourse.readcircle.domain.purchase.exception.PurchaseException;
import org.prgrms.devcourse.readcircle.domain.purchase.repository.PurchaseRepository;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserFindRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus.REQUEST_WAITING;


@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;
    private final UserFindRepository userFindRepository;
    private final PagingUtil pagingUtil;

    @Override
    public PurchaseDTO register(PurchaseDTO purchaseDTO) {
        try{
            User user = userFindRepository.findByUserId(purchaseDTO.getUserId())
                                            .orElseThrow(PurchaseException.NOT_FOUND_USER_EXCEPTION::getTaskException);
            Purchase purchase = purchaseDTO.toEntity();
            purchase.setUser(user);
            purchase.changePurchaseStatus(REQUEST_WAITING);
            purchaseRepository.save(purchase);
            return new PurchaseDTO(purchase);
        }catch (Exception e){
            throw PurchaseException.NOT_REGISTERED_EXCEPTION.getTaskException();
        }
    }

    @Override
    public Page<PurchaseDTO> readByStatus(PurchaseStatus purchaseStatus) {
        Page<Purchase> purchases = null;
        if(purchaseStatus == null)  {
            Pageable pageable = pagingUtil.getPageable();
            purchases = purchaseRepository.findAll(pageable);
        }else{
            Pageable pageable = pagingUtil.getPageableForStatus("desc");
            purchases = purchaseRepository.findByStatus(purchaseStatus, pageable);
        }
        return purchases.map(PurchaseDTO::new);
    }

    @Override
    public Page<PurchaseDTO> readByNickname(String nickname) {
        Pageable pageable = pagingUtil.getPageable();
        Page<Purchase> purchases = purchaseRepository.findByNickname(nickname, pageable);
        return purchases.map(PurchaseDTO::new);
    }

    @Override
    public PurchaseDTO pricing(Long purchaseId, PricingDTO pricingDTO) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(PurchaseException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            purchase.changePrice(pricingDTO.getPrice());
            purchase.changePurchaseStatus(pricingDTO.getPurchaseStatus());
            purchase.changeBookCondition(pricingDTO.getBookCondition());

            purchaseRepository.save(purchase);
            return new PurchaseDTO(purchase);

        }catch (Exception e){
            throw PurchaseException.NOT_MODIFIED_EXCEPTION.getTaskException();
        }
    }

    @Override
    public void delete(Long purchaseId) {
        purchaseRepository.findById(purchaseId).orElseThrow(PurchaseException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            purchaseRepository.deleteById(purchaseId);
        }catch (Exception e){
            throw PurchaseException.NOT_REMOVED_EXCEPTION.getTaskException();
        }
    }
}
