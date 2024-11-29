package org.prgrms.devcourse.readcircle.domain.purchase.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.util.PagingUtil;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.service.NotificationService;
import org.prgrms.devcourse.readcircle.common.notification.service.SSEService;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.dto.PurchaseDTO;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.Purchase;
import org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus;
import org.prgrms.devcourse.readcircle.domain.purchase.exception.PurchaseException;
import org.prgrms.devcourse.readcircle.domain.purchase.repository.PurchaseRepository;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType.PURCHASE_PRICE_SET;
import static org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType.PURCHASE_RESPONSE_WAITING;
import static org.prgrms.devcourse.readcircle.domain.purchase.entity.enums.PurchaseStatus.RESPONSE_WAITING;


@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;
    private final NotificationService notificationService;
    private final UserService userService;
    private final SSEService sseService;
    private final PagingUtil pagingUtil;

    @Override
    public PurchaseDTO register(PurchaseDTO purchaseDTO) {
        try{
            User user = userService.findUserByUserId(purchaseDTO.getUserId());

            Purchase purchase = purchaseDTO.toEntity();
            purchase.setUser(user);
            purchase.changePurchaseStatus(RESPONSE_WAITING);
            purchaseRepository.save(purchase);

            if(purchase!=null){
                String message = "새로운 매입 신청이 있습니다.";
                Notification notification = notificationService.saveNotification(
                        "admin",
                        message,
                        PURCHASE_RESPONSE_WAITING
                );
                sseService.sendNotification("admin", message, PURCHASE_RESPONSE_WAITING, notification.getNotificationId());
            }

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
        String userId = purchase.getUser().getUserId();
        String isbn = purchase.getIsbn();

        try{
            purchase.changePrice(pricingDTO.getPrice());
            purchase.changePurchaseStatus(pricingDTO.getPurchaseStatus());
            purchase.changeBookCondition(pricingDTO.getBookCondition());

            purchaseRepository.save(purchase);
            if(pricingDTO.getPurchaseStatus()==PurchaseStatus.PRICE_SET){
                String message = "판매자님!  isbn 번호 - "+ isbn +" 의 가격이 결정되었습니다. 판매 목록에서 확인하실 수 있습니다.";
                Notification notification = notificationService.saveNotification(
                        userId,
                        message,
                        PURCHASE_PRICE_SET
                );

                sseService.sendNotification(userId, message, notification.getType(), notification.getNotificationId());
            }

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
