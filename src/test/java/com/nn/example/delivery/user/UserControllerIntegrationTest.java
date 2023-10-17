package com.nn.example.delivery.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldCreateUser() throws Exception {
        //given
        CreateUserRequest userRequest = new CreateUserRequest("John", "Doe", "123 Street");
        String userJsonRequest = objectMapper.writeValueAsString(userRequest);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.address").value("123 Street"))
                .andReturn();
    }

    @Sql(scripts = "/createUser.sql")
    @Test
    void shouldCreateBankAccount() throws Exception {
        //given
        CreateBankAccountRequest bankAccountRequest = new CreateBankAccountRequest(2L, "USD", new BigDecimal("1000.00"));
        String bankAccountJsonRequest = objectMapper.writeValueAsString(bankAccountRequest);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankAccountJsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cif").value("2"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.balance").value("1000.0"))
                .andExpect(jsonPath("$.accountIdNumber").isNotEmpty())
                .andExpect(jsonPath("$.accountNumber").isNotEmpty())
                .andReturn();
    }

    @Sql(scripts = "/createUserWithOneAccounts.sql")
    @Test
    void getAccountInfo() throws Exception {
        //given
        String accountIdNumber = "aaf3fb98-b40d-4cc7-adfa-3a6ccec67268";
        mockMvc.perform(get("/users/account/" + accountIdNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value("aaf3fb98-b40d-4cc7-adfa-3a6ccec67268"))
                .andExpect(jsonPath("$.accountNumber").value("7930038199"))
                .andExpect(jsonPath("$.cif").value(124))
                .andExpect(jsonPath("$.currency").value("PLN"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

}
