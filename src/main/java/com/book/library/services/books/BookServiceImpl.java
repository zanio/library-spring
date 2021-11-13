package com.book.library.services.books;

import com.book.library.dao.BookDao;
import com.book.library.entities.Book;
import com.book.library.entities.Category;
import com.book.library.entities.Favorite;
import com.book.library.exceptions.custom.EntityNotFoundException;
import com.book.library.repository.BookRepository;
import com.book.library.services.category.CategoryService;
import com.book.library.util.response.UpdateObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;

@Service
@Slf4j
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepositoryImpl;
    @Autowired
    private CategoryService categoryServiceImpl;
    @Override
    public Book findOne(Integer bookId) {
        return bookRepositoryImpl.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException(Book.class, "Id", bookId.toString())
        );
    }

    @Override
    public Book create(BookDao bookDao) {
        Book book = new Book();
        book.setAuthor(book.getAuthor());
        if(bookDao.getDownloadUrl()!=null){
            book.setDownloadUrl(book.getDownloadUrl());
        }
        book.setIsbn(bookDao.getIsbn());
        book.setAuthor(bookDao.getAuthor());
        book.setPublishedDate(bookDao.getPublishedDate());
        book.setTitle(bookDao.getTitle());
        book.setTotalPages(bookDao.getTotalPages());
        book.setPublisherId(bookDao.getPublisherId());
        bookRepositoryImpl.save(book);
        return book;
    }

    @Override
    public Book update(BookDao bookDao,Integer bookId) throws InvocationTargetException,
            IllegalAccessException, NoSuchMethodException {
        Book book =  findOne(bookId);
        log.info("bookDao->{}", bookDao);

        UpdateObjectHelper.updateFieldValue(bookDao,book);
        bookRepositoryImpl.save(book);
        log.info("book->{}", book);
        return book;
    }

    @Override
    public Book addBookToCategory(Integer bookId, Integer categoryId) {
        Category category = categoryServiceImpl.findOne(categoryId);
        Book book =  findOne(bookId);
        book.setCategory(category);
        bookRepositoryImpl.save(book);
        return book;
    }

    @Override
    public void deleteBook(Integer bookId) {
        Book book =  findOne(bookId);
         bookRepositoryImpl.delete(book);
    }

    @Override
    public Page<Book> listBooks(Pageable pageable) {
        return bookRepositoryImpl.findAll(pageable);
    }

}
