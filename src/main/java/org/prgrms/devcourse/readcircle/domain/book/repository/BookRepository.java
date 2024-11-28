package org.prgrms.devcourse.readcircle.domain.book.repository;

import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
