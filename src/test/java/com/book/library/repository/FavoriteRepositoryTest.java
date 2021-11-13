package com.book.library.repository;

import com.book.library.entities.Book;
import com.book.library.entities.Category;
import com.book.library.entities.Favorite;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
@SpringBootTest(properties = "spring.profiles.active=test")
@Slf4j
class FavoriteRepositoryTest {
    @Autowired
    FavoriteRepository favoriteRepositoryImpl;
    @Autowired
    BookRepository bookRepository;
    Favorite favorite1;
    Favorite favorite2;
    Favorite favorite3;

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
        favorite1 = new Favorite();
        favorite1.setBooks(Set.of(book1,book2,book3));

        favorite2 = new Favorite();
        favorite2.setBooks(Set.of(book1,book3));

        favorite3 = new Favorite();
        favorite3.setBooks(Set.of(book2,book3));
        favoriteRepositoryImpl.saveAll(Set.of(favorite1, favorite2, favorite3));



    }

    @AfterEach
    void tearDown() {
        favoriteRepositoryImpl.deleteAll(Set.of(favorite1, favorite2, favorite3));
    }

    @Test
    void testThatCategoryCanBeFound() {
        Optional<Favorite> foundFavorite = favoriteRepositoryImpl.findById(favorite1.getId());
        assertThat(foundFavorite.isPresent()).isTrue();
    }

    @Test
    void testThatCategoryCanBeSaved() {
        Favorite  favorite1 = new Favorite();
        Set<Book> booknew1 = bookRepository.findAllByIdIn(Set.of(book2.getId(),book1.getId(),book3.getId()));
        favorite1.setBooks(booknew1);
        favoriteRepositoryImpl.save(favorite1);
        log.info("favorite -> {}", favorite1);
        assertThat(favorite1.getId()).isNotNull();
    }
    @Test
    void testThatCategoryCanBeFetchedWithPagination() {
        Page<Favorite> page =  favoriteRepositoryImpl.findAll(Pageable.ofSize(1));
        assertThat(page.getTotalElements()).isEqualTo(4);
    }
}