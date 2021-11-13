package com.book.library.clients;

import com.book.library.dao.FavoriteDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/favorite.sql","classpath:db/init.sql"})
@Sql(scripts = "classpath:db/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class FavoriteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenFavorite_is_created_thenReturnFavorite() throws Exception {

        FavoriteDao  favoriteDao = new FavoriteDao();
        favoriteDao.setBooks(Set.of(16,17,18));

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/favorite")
                .contentType("application/json")
                .content(mapper.writeValueAsString(favoriteDao)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void findAllFavorites() throws Exception {

        this.mockMvc.perform(get("/api/v1/favorite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAFavoriteTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/favorite/39"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}