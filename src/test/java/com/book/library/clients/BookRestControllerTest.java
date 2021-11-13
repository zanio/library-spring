package com.book.library.clients;

import com.book.library.dao.BookDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/init.sql"})
@Sql(scripts = "classpath:db/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenBook_is_created_thenReturnBook() throws Exception {

        BookDao  bookDao = BookDao.builder().author(faker.book().author()).downloadUrl("http://github.com/zan")
                .isbn("007462542X").publishedDate(new Date())
                .publisherId("feowj4")
                .title(faker.book().title())
                .totalPages(404).build();

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/book")
                .contentType("application/json")
                .content(mapper.writeValueAsString(bookDao)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void findAllBooks() throws Exception {

        this.mockMvc.perform(get("/api/v1/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findABookTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/book/24"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteABook() throws Exception {

        this.mockMvc.perform(delete("/api/v1/book/25"))
                .andExpect(status().isOk());
    }


    @Test
    void canUpdateBook() throws Exception {
        BookDao  bookDao = BookDao.builder().author(faker.book().author()).downloadUrl("http://github.com/zan")
                .isbn("007462542X").publishedDate(new Date())
                .publisherId("feowj4")
                .title(faker.book().title())
                .totalPages(300).build();
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(patch("/api/v1/book/26")
                .contentType("application/json")
                .content(mapper.writeValueAsString(bookDao)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}