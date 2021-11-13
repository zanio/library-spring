package com.book.library.repository;

import com.book.library.entities.Book;
import com.book.library.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Set<Book> findAllByCategory(@Param("category") Category category);
    Set<Book> findAllByIdIn(Set<Integer> bookIds);
}
