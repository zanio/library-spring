package com.book.library.services.books;

import com.book.library.dao.BookDao;
import com.book.library.entities.Book;
import com.book.library.entities.Category;
import com.book.library.exceptions.custom.EntityNotFoundException;
import com.book.library.repository.BookRepository;
import com.book.library.services.category.CategoryService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryService categoryServiceImpl;
    Book book1;
    BookDao bookDao;
    Book book2;
    Book book3;
    Category category;
    Faker faker = new Faker();

    @InjectMocks
    BookService bookServiceImpl = new BookServiceImpl();


    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setId(1);
        book1.setAuthor(faker.name().fullName());
        book1.setTotalPages(200);
        book1.setPublisherId("EWOEEOI489348947");
        book1.setIsbn("007462542X");
        book1.setTitle(faker.book().title());
        book1.setPublishedDate(new Date());

        book2 = new Book();
        book2.setId(3);
        book2.setAuthor(faker.name().fullName());
        book2.setTotalPages(1001);
        book2.setPublisherId(faker.book().publisher());
        book2.setIsbn("007462542X");
        book2.setTitle(faker.book().title());
        book2.setPublishedDate(new Date());

        book3 = new Book();
        book3.setId(4);
        book3.setAuthor(faker.name().fullName());
        book3.setTotalPages(1001);
        book3.setPublisherId(faker.book().publisher());
        book3.setIsbn("007462542X");
        book3.setTitle(faker.book().title());
        book3.setPublishedDate(new Date());

        category = new Category();
        category.setName("dating");
        category.setId(5);
        category.setBooks(Set.of(book3,book2,book1));

        MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll(Set.of(book1,book2,book3));
    }

    @Test
    void findOne() {
        when(bookRepository.findById(4))
                .thenReturn(Optional.of(book3));
        Book foundBook = bookServiceImpl.findOne(4);
        assertThat(foundBook).isNotNull();
    }

    @Test
    void findOne_throwNotFoundException() {
        when(bookRepository.findById(4))
                .thenReturn(Optional.of(book3));
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    bookServiceImpl.findOne(5);
                }
        );
    }

    @Test
    void create() {
        bookDao = BookDao.builder().author(faker.book().author()).downloadUrl("http://github.com/zan")
                .isbn("007462542X").publishedDate(new Date())
                .publisherId("feowj4")
                .title(faker.book().title())
                .totalPages(40).build();
      Book book =  bookServiceImpl.create(bookDao);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void update() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        bookDao = BookDao.builder().author(faker.book().author()).downloadUrl("http://github.com/zan")
                .isbn("007462542X").publishedDate(new Date())
                .publisherId("feowj4")
                .title(faker.book().title())
                .totalPages(404).build();
        when(bookRepository.findById(4))
                .thenReturn(Optional.of(book3));
        Book book =  bookServiceImpl.update(bookDao,4);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void addBookToCategory() {
        when(categoryServiceImpl.findOne(5))
                .thenReturn(category);
        when(bookRepository.findById(4))
                .thenReturn(Optional.of(book3));
        Book book =  bookServiceImpl.addBookToCategory(4,5);
        verify(bookRepository, times(1)).save(book);
        verify(bookRepository, times(1)).findById(4);
        verify(categoryServiceImpl, times(1)).findOne(5);



    }

    @Test
    void deleteBook() {
        doNothing().when(bookRepository).delete(book1);
        when(bookRepository.findById(1))
                .thenReturn(Optional.of(book1));
        bookServiceImpl.deleteBook(1);

        verify(bookRepository, times(1)).delete(book1);

    }

    @Test
    void listBooks() {
        Page<Book> books = new PageImpl<>(Arrays.asList(book3,book1,book2));
        when(bookRepository.findAll(Pageable.ofSize(1)))
                .thenReturn(books);
        bookServiceImpl.listBooks(Pageable.ofSize(1));
        verify(bookRepository, times(1)).findAll(Pageable.ofSize(1));

    }
}