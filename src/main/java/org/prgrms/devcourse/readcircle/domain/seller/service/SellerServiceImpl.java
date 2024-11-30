package org.prgrms.devcourse.readcircle.domain.seller.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.common.enums.BookCondition;
import org.prgrms.devcourse.readcircle.common.enums.BookProcess;
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
            return new SellerDTO(savedSeller);
        }catch (Exception e){
            System.err.println(e.getMessage());
            throw SellerException.NOT_REGISTERED_SELLER_EXCEPTION.getTaskException();
        }
    }

    @Override
    public Page<SellerDTO> findByUserId(String userId, BookProcess process){
        Pageable pageable = pagingUtil.getPageable();
        Page<Seller> sellers = sellerRepository.getSellerByUserId(userId, process, pageable);
        return sellers.map(SellerDTO::new);
    }

    @Override
    public Page<SellerDTO> findByAdmin(BookProcess process){
        Pageable pageable = pagingUtil.getPageable();
        Page<Seller> sellers = sellerRepository.getSellerByAdmin(process, pageable);
        return sellers.map(SellerDTO::new);
    }

    @Override
    public void pricing(Long sellerId, PricingDTO pricingDTO){
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(SellerException.NOT_FOUND_SELLER_EXCEPTION::getTaskException);
        BookResponse response = bookService.getBook(pricingDTO.getBookId());
        Book book;
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
                    response.getBookCondition(),
                    response.getPrice(),
                    response.getProcess(),
                    response.isForSale()
            );
            bookService.updateBook(response.getId(), request);
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
