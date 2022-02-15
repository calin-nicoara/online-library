package com.example.onlinelibrary.customers;

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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/sql/customers.sql")
    @Transactional
    public void testFindCustomers() throws Exception {
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/customer")).andReturn();

        List<CustomerDTO> customerDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(1, customerDTOS.size());

        CustomerDTO customerDTO = customerDTOS.get(0);

        Assertions.assertEquals(-1L, customerDTO.getId());
        Assertions.assertEquals("John Doe", customerDTO.getName());
        Assertions.assertEquals("john.doe", customerDTO.getUsername());
        Assertions.assertEquals("john.doe@test.com", customerDTO.getEmail());
    }
    

    @Test
    @Sql("/sql/customers.sql")
    @Transactional
    public void testFindCustomerById() throws Exception {
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/customer/-1")).andReturn();

        CustomerDTO customerDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDTO.class);

        Assertions.assertEquals(-1L, customerDTO.getId());
        Assertions.assertEquals("John Doe", customerDTO.getName());
        Assertions.assertEquals("john.doe", customerDTO.getUsername());
        Assertions.assertEquals("john.doe@test.com", customerDTO.getEmail());
    }

    @Test
    @Sql("/sql/customers.sql")
    @Transactional
    public void testFindCustomerByIdNotFound() throws Exception {
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/customer/-10")).andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Transactional
    public void testAddCustomer() throws Exception {
        CreateCustomerDTO createCustomerDTO = CreateCustomerDTO.builder()
                .name("Mary Doe")
                .username("mary.doe")
                .email("mary.doe@test.com")
                .password("password")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCustomerDTO))).andReturn();

        CustomerDTO customerDTOFromEndpoint = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDTO.class);

        MvcResult mvcResultGet = this.mvc.perform(MockMvcRequestBuilders.get("/customer/" + customerDTOFromEndpoint.getId().toString())).andReturn();

        CustomerDTO customerDTO = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), CustomerDTO.class);

        CustomerDTO customerDTOExpected = CustomerDTO.builder()
                .id(1L)
                .name("Mary Doe")
                .username("mary.doe")
                .email("mary.doe@test.com")
                .build();

        Assertions.assertEquals(customerDTOExpected,customerDTO);
    }

    @Test
    @Transactional
    public void testAddCustomerEmailAndUsernameTheSameError() throws Exception {
        CreateCustomerDTO createCustomerDTO = CreateCustomerDTO.builder()
                .name("Mary Doe")
                .username("mary.doe@test.com")
                .email("mary.doe@test.com")
                .password("password")
                .build();

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCustomerDTO))).andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

        MvcResult mvcResultGetCustomers = this.mvc.perform(MockMvcRequestBuilders.get("/customer")).andReturn();

        List<CustomerDTO> customerDTOS = objectMapper.readValue(mvcResultGetCustomers.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(0, customerDTOS.size());
    }

    @Test
    @Transactional
    @Sql("/sql/customers.sql")
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTOToUpdate = CustomerDTO.builder()
                .id(-1L)
                .name("Mary Doe")
                .email("mary.doe@test.com")
                .build();


        this.mvc.perform(MockMvcRequestBuilders.put("/customer/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTOToUpdate))).andReturn();

        MvcResult mvcResultGet = this.mvc.perform(MockMvcRequestBuilders.get("/customer/-1")).andReturn();

        CustomerDTO customerDTO = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), CustomerDTO.class);

        CustomerDTO customerDTOExpected = CustomerDTO.builder()
                .id(-1L)
                .name("Mary Doe")
                .username("john.doe")
                .email("mary.doe@test.com")
                .build();

        Assertions.assertEquals(customerDTOExpected, customerDTO);
    }

    @Test
    @Sql("/sql/customers.sql")
    @Transactional
    public void testDeleteCustomerById() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.delete("/customer/-1"));

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/customer/-1")).andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

}
