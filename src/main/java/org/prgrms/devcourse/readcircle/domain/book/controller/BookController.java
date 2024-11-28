package org.prgrms.devcourse.readcircle.domain.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.response.ApiResponse;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookCreateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.request.BookUpdateRequest;
import org.prgrms.devcourse.readcircle.domain.book.dto.response.BookResponse;
import org.prgrms.devcourse.readcircle.domain.book.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    // 책 생성
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> registerBook(@Valid @RequestBody BookCreateRequest request) {
         bookService.createBook(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 책 상세 조회 (단건)
    @GetMapping("/detail/{bookId}")
    public ResponseEntity<ApiResponse> getBook(@PathVariable Long bookId) {
        BookResponse book = bookService.getBook(bookId);
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    // 책 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BookResponse> books = bookService.getBooks(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    // 책 수정
    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookUpdateRequest request) {
        bookService.updateBook(bookId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 책 삭제
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
