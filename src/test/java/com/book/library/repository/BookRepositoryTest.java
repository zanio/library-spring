package com.book.library.repository;

import com.book.library.entities.Book;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@Slf4j
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    Book book1;
    Book book2;
    Book book3;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setAuthor(faker.name().fullName());
        book1.setTotalPages(200);
        book1.setPublisherId("EWOEEOI489348947");
        book1.setIsbn("007462542X");
        book1.setTitle(faker.book().title());
        book1.setPublishedDate(new Date());

        book2 = new Book();
        book2.setAuthor(faker.name().fullName());
        book2.setTotalPages(1001);
        book2.setPublisherId(faker.book().publisher());
        book2.setIsbn("007462542X");
        book2.setTitle(faker.book().title());
        book2.setPublishedDate(new Date());

        book3 = new Book();
        book3.setAuthor(faker.name().fullName());
        book3.setTotalPages(1001);
        book3.setPublisherId(faker.book().publisher());
        book3.setIsbn("007462542X");
        book3.setTitle(faker.book().title());
        book3.setPublishedDate(new Date());

        bookRepository.saveAll(Set.of(book1,book2,book3));
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll(Set.of(book1,book2,book3));
    }

    @Test
    void testThatBookCanBeFound() {
        Optional<Book> foundBook = bookRepository.findById(book1.getId());
        assertThat(foundBook.isPresent()).isTrue();
    }

    @Test
    void testThatBookCanBeSaved() {
        Book newBook = new Book();
        newBook.setAuthor(faker.name().fullName());
        newBook.setTotalPages(1001);
        newBook.setPublisherId(faker.book().publisher());
        newBook.setIsbn("007462542X");
        newBook.setTitle(faker.book().title());
        newBook.setPublishedDate(new Date());
        bookRepository.save(newBook);
        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    void testThatBookCanBeUpdated() {
        book3.setAuthor(faker.name().fullName());
        book3.setTotalPages(500);
        book3.setPublisherId(faker.book().publisher());
        book3.setIsbn("007462542X");
        book3.setTitle(faker.book().title());
        book3.setPublishedDate(new Date());
        bookRepository.save(book3);
        assertThat(book3.getTotalPages()).isEqualTo(500);
    }

    @Test
    void testThatBookCanBeFetchedWithPagination() {
         Page<Book> page =  bookRepository.findAll(Pageable.ofSize(1));
        assertThat(page.getTotalElements()).isEqualTo(bookRepository.count());
    }

    @Test
    void testThatBookCanBeDeleted() {
        bookRepository.deleteById(book1.getId());
        Optional<Book> foundBook = bookRepository.findById(book1.getId());

        assertThat(foundBook.isPresent()).isFalse();
    }

    @Test
    void testThatBooksCanBeDeleted() {
        bookRepository.deleteAll(Set.of(book1,book2,book3));
       Iterable<Book> books = bookRepository.findAllById(Set.of(book1.getId(),book2.getId(),book3.getId()));
       books.forEach(it->{
           assertThat(it).isNull();

       });
    }
}