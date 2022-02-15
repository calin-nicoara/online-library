package com.example.onlinelibrary.books;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String testToken = "42062adb-c468-48ce-a8b6-c097f2508f89";
    private static final String authHeader = "X-CUSTOM-AUTH-TOKEN";

    @Test
    @Sql({"/sql/login.sql", "/sql/books.sql"})
    @Transactional
    public void testFindBooks() throws Exception {
        BookDTO bookDTOExpected = BookDTO.builder()
                .id(-1L)
                .title("Moby Dick")
                .author("Herman Melville")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/book").header(authHeader, testToken)).andReturn();

        List<BookDTO> bookDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(1, bookDTOS.size());

        BookDTO bookDTO = bookDTOS.get(0);

        Assertions.assertEquals(bookDTOExpected, bookDTO);
    }


    @Test
    @Sql({"/sql/login.sql", "/sql/books.sql"})
    @Transactional
    public void testFindBookById() throws Exception {
        BookDTO bookDTOExpected = BookDTO.builder()
                .id(-1L)
                .title("Moby Dick")
                .author("Herman Melville")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/book/-1").header(authHeader, testToken)).andReturn();

        BookDTO bookDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookDTO.class);

        Assertions.assertEquals(bookDTOExpected, bookDTO);
    }

    @Test
    @Sql({"/sql/login.sql", "/sql/books.sql"})
    @Transactional
    public void testFindBookByIdNotFound() throws Exception {
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/book/-10").header(authHeader, testToken)).andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }


    @Test
    @Transactional
    @Sql({"/sql/login.sql"})
    public void testAddBook() throws Exception {
        BookDTO bookDTOToCreate = BookDTO.builder()
                .title("Tarzan")
                .author("Edgar Rice Burroughs")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/book")
                .header(authHeader, testToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTOToCreate))).andReturn();

        BookDTO bookDTOFromEndpoint = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookDTO.class);

        MvcResult mvcResultGet = this.mvc.perform(MockMvcRequestBuilders.get("/book/" + bookDTOFromEndpoint.getId().toString()).header(authHeader, testToken)).andReturn();

        BookDTO bookDTO = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), BookDTO.class);

        BookDTO bookDTOExpected = BookDTO.builder()
                .id(1L)
                .title("Tarzan")
                .author("Edgar Rice Burroughs")
                .build();

        Assertions.assertEquals(bookDTOExpected, bookDTO);
    }

    @Test
    @Transactional
    @Sql({"/sql/login.sql", "/sql/books.sql"})
    public void testUpdateBook() throws Exception {
        BookDTO bookDTOToUpdate = BookDTO.builder()
                .id(-1L)
                .title("Moby Dick 2")
                .author("Herman Melville")
                .build();


        this.mvc.perform(MockMvcRequestBuilders.put("/book/-1")
                .header(authHeader, testToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTOToUpdate))).andReturn();

        MvcResult mvcResultGet = this.mvc.perform(MockMvcRequestBuilders.get("/book/-1").header(authHeader, testToken)).andReturn();

        BookDTO bookDTO = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), BookDTO.class);

        Assertions.assertEquals(bookDTOToUpdate, bookDTO);
    }

    @Test
    @Sql({"/sql/login.sql", "/sql/books.sql"})
    @Transactional
    public void testDeleteBookById() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.delete("/book/-1").header(authHeader, testToken));

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/books/-1").header(authHeader, testToken)).andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Sql("/sql/books.sql")
    @Transactional
    public void testInvalidToken() throws Exception {
        MvcResult mvcResult =  this.mvc.perform(MockMvcRequestBuilders.get("/book").header(authHeader, "INVALID_TOKEN")).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
}
