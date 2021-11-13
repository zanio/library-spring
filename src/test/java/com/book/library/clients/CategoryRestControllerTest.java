package com.book.library.clients;

import com.book.library.dao.CategoryDao;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/category.sql"})
@Sql(scripts = "classpath:db/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenCategory_is_created_thenReturnCategory() throws Exception {

        CategoryDao  categoryDao = new CategoryDao();
        categoryDao.setNameOfCategory("gaming");

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/category")
                .contentType("application/json")
                .content(mapper.writeValueAsString(categoryDao)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void findAllCategories() throws Exception {

        this.mockMvc.perform(get("/api/v1/category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findACategoryTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/category/35"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteACategory() throws Exception {

        this.mockMvc.perform(delete("/api/v1/category/35"))
                .andExpect(status().isOk());
    }


    @Test
    void canUpdateCategory() throws Exception {
        CategoryDao  categoryDao =new CategoryDao();
        categoryDao.setNameOfCategory("education");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(patch("/api/v1/category/37")
                .contentType("application/json")
                .content(mapper.writeValueAsString(categoryDao)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}