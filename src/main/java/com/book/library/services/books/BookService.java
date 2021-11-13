package com.book.library.services.books;

import com.book.library.dao.BookDao;
import com.book.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;

public interface BookService {
    Book findOne(Integer favoriteId);
    Book create(BookDao bookDao);
    Book update(BookDao bookDao,Integer bookId) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    Book addBookToCategory(Integer bookId, Integer categoryId);
    void deleteBook(Integer bookId);
    Page<Book> listBooks(Pageable pageable);
}
