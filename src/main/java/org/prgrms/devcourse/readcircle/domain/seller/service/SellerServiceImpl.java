package org.prgrms.devcourse.readcircle.domain.seller.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
import org.prgrms.devcourse.readcircle.common.notification.entity.Notification;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;
import org.prgrms.devcourse.readcircle.common.notification.service.NotificationServiceImpl;
import org.prgrms.devcourse.readcircle.common.notification.service.SSEService;
import org.prgrms.devcourse.readcircle.common.util.PagingUtil;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookCreateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookUpdateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.response.BookResponse;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.book.service.BookService;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.PricingDTO;
import org.prgrms.devcourse.readcircle.domain.seller.dto.request.SellerBookRequest;
import org.prgrms.devcourse.readcircle.domain.seller.dto.response.SellerDTO;
import org.prgrms.devcourse.readcircle.domain.seller.entity.Seller;
import org.prgrms.devcourse.readcircle.domain.seller.exception.SellerException;
import org.prgrms.devcourse.readcircle.domain.seller.repository.SellerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService{
    private final SellerRepository sellerRepository;
    private final BookService bookService;
    private final PagingUtil pagingUtil;
    private final SSEService sseService;
    private final NotificationServiceImpl notificationService;

    @Override
    public SellerDTO register(SellerBookRequest request, String userId){
        Book book;
        try{
            BookCreateRequest bookRequest = new BookCreateRequest(
                    request.getTitle(),
                    request.getBookCategory(),
                    request.getIsbn(),
                    request.getAuthor(),
                    request.getPublisher(),
                    request.getPublishDate(),
                    request.getDescription(),
                    request.getThumbnailUrl(),
                    request.getBookCondition(),
                    request.getPrice(),
                    BookProcess.WAITING
            );
            book = bookService.createBook(bookRequest);
            System.out.println("Seller - "+ book.getProcess());
        }catch (Exception e){
            throw SellerException.NOT_REGISTERED_BOOK_EXCEPTION.getTaskException();
        }

        try {
            Seller seller = new Seller(
                    book,
                    userId,
                    request.getBank(),
                    request.getAccount(),
                    request.getAccountOwner()
            );
            Seller savedSeller = sellerRepository.save(seller);

            String message = book.getTitle()+"("+book.getIsbn()+") 도서 판매 신청이 완료되었습니다.";
            NotificationType type = NotificationType.PURCHASE_RESPONSE_WAITING;
            Notification noti = notificationService.saveNotification(userId, message, type);
            sseService.sendNotification(userId, message, type, noti.getNotificationId());

            return new SellerDTO(savedSeller);
        }catch (Exception e){
            System.err.println(e.getMessage());
            throw SellerException.NOT_REGISTERED_SELLER_EXCEPTION.getTaskException();
        }
    }

    @Override
    public Page<SellerDTO> findByUserId(String userId, BookProcess process, int page, int size){
        Pageable pageable = pagingUtil.getNewPageable(page, size);
        Page<Seller> sellers = sellerRepository.getSellerByUserId(userId, process, pageable);
        return sellers.map(SellerDTO::new);
    }

    @Override
    public Page<SellerDTO> findByAdmin(BookProcess process, int page, int size){
        Pageable pageable = pagingUtil.getNewPageable(page, size);
        Page<Seller> sellers = sellerRepository.getSellerByAdmin(process, pageable);
        return sellers.map(SellerDTO::new);
    }

    @Override
    public BookResponse pricing(Long sellerId, PricingDTO pricingDTO){
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(SellerException.NOT_FOUND_SELLER_EXCEPTION::getTaskException);
        Book response = seller.getBook();
        try{
            seller.changeDepositAmount(pricingDTO.getDepositAmount());
            BookUpdateRequest request = new BookUpdateRequest(
                    response.getTitle(),
                    response.getCategory(),
                    response.getIsbn(),
                    response.getAuthor(),
                    response.getPublisher(),
                    response.getPublishDate(),
                    response.getDescription(),
                    response.getThumbnailUrl(),
                    pricingDTO.getBookCondition(),
                    response.getPrice(),
                    pricingDTO.getProcess(),
                    response.isForSale()
            );
            bookService.updateBook(response.getId(), request);
            return BookResponse.from(response);
        }catch (Exception e){
            throw SellerException.NOT_MODIFIED_EXCEPTION.getTaskException();
        }
    }

    @Override
    public void delete(Long sellerId){
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(SellerException.NOT_FOUND_SELLER_EXCEPTION::getTaskException);
        try{
            sellerRepository.deleteById(sellerId);
        } catch(Exception e){
            throw SellerException.NOT_REMOVED_EXCEPTION.getTaskException();
        }
    }
}
