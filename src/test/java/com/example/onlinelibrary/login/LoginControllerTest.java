package com.example.onlinelibrary.login;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String authHeader = "X-CUSTOM-AUTH-TOKEN";

    @Test
    @Transactional
    @Sql({"/sql/customers.sql", "/sql/books.sql"})
    public void testLoginAndGetTokenAndUse() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .password("password")
                .username("john.doe")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        String token = mvcResult.getResponse().getContentAsString();

        Assertions.assertNotNull(token);

        MvcResult mvcResultForFindBook = this.mvc.perform(MockMvcRequestBuilders.get("/book/-1").header(authHeader, token)).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), mvcResultForFindBook.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Sql({"/sql/customers.sql"})
    public void testLoginNotWorking() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .password("password_not_correct")
                .username("john.doe")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))).andReturn();

        Assertions.assertEquals("Username or password not correct!", mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
}
