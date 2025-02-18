package com.br.authorizer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SecuredController.class)
class SecuredControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser")
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/secured/hello")
                        .principal(() -> "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bem-vindo!"))
                .andExpect(jsonPath("$.user").value("testuser"));
    }

}
