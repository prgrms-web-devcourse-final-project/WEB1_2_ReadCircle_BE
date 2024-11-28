package org.prgrms.devcourse.readcircle.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookCreateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookUpdateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.response.BookResponse;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.book.exception.BookException;
import org.prgrms.devcourse.readcircle.domain.book.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 책 등록
    @Transactional
    public void createBook(BookCreateRequest request) {
        Book book = Book.builder()
                .category(request.getBookCategory())
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .description(request.getDescription())
                .isbn(request.getIsbn())
                .publishDate(request.getPublishDate())
                .thumbnailUrl(request.getThumbnailUrl())
                .bookCondition(request.getBookCondition())
                .price(request.getPrice())
                .build();

        bookRepository.save(book);
    }

    // 책 조회 (단건)
    public BookResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookException.NOT_FOUND::get);
        return BookResponse.from(book);
    }

    // 책 목록 조회
    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookResponse::from);
    }

    // 책 수정
    @Transactional
    public void updateBook(Long bookId, BookUpdateRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookException.NOT_FOUND::get);

        book.updateBookInfo(
                request.getCategory(),
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                request.getDescription(),
                request.getIsbn(),
                request.getPublishDate(),
                request.getThumbnailUrl(),
                request.getBookCondition(),
                request.getPrice(),
                request.isForSale()
        );
    }

    // 책 삭제
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookException.NOT_FOUND::get);
        bookRepository.delete(book);
    }


}
