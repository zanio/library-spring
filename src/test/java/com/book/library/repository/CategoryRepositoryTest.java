package com.book.library.repository;

import com.book.library.entities.Category;
import com.book.library.entities.Category;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@Slf4j
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepositoryImpl;
    Category category1;
    Category category2;
    Category category3;
    @BeforeEach
    void setUp() {
        category1 = new Category("gaming");
        category2 = new Category("education");
        category3 = new Category("sport");
        categoryRepositoryImpl.saveAll(Set.of(category1, category2, category3));
    }

    @AfterEach
    void tearDown() {
        categoryRepositoryImpl.deleteAll(Set.of(category1, category2, category3));
    }

    @Test
    void testThatCategoryCanBeFound() {
        Optional<Category> foundCategory = categoryRepositoryImpl.findById(category1.getId());
        assertThat(foundCategory.isPresent()).isTrue();
    }

    @Test
    void testThatCategoryCanBeSaved() {
        Category newCategory = new Category("government");
        categoryRepositoryImpl.save(newCategory);
        assertThat(newCategory.getId()).isNotNull();
    }
    @Test
    void testThatCategoryCanBeFetchedWithPagination() {
        Page<Category> page =  categoryRepositoryImpl.findAll(Pageable.ofSize(1));
        assertThat(page.getTotalElements()).isEqualTo(4);
    }

    @Test
    void testThatCategoryCanBeDeleted() {
        categoryRepositoryImpl.deleteById(category1.getId());
        Optional<Category> foundCategory = categoryRepositoryImpl.findById(category1.getId());

        assertThat(foundCategory.isPresent()).isFalse();
    }

    @Test
    void testThatCategoriesCanBeDeleted() {
        categoryRepositoryImpl.deleteAll(Set.of(category1, category2, category3));
        Iterable<Category> books = categoryRepositoryImpl.findAllById(Set.of(category1.getId(),category2.getId(),category3.getId()));
        books.forEach(it->{
            assertThat(it).isNull();

        });
    }
}