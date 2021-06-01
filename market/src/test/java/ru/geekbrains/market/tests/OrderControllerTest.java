package ru.geekbrains.market.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    public void authorization() throws Exception {
        String jsonRequest = "{\n" +
                "\t\"username\": \"bob\",\n" +
                "\t\"password\": \"100\"\n" +
                "}";
        MvcResult result = mockMvc.perform(post("/auth")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        token = result.getResponse().getContentAsString();
        token = token.replace("{\"token\":\"", "").replace("\"}", "");
    }

    @Test
    public void getUserAuthorized() throws Exception {
        mockMvc.perform(get("/api/v1/orders").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsersOrderTest() throws Exception {
        mockMvc.perform(get("/api/v1/orders/1").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        String result = mockMvc.perform(get("/api/v1/orders/1").header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(result.contains("1200.0"));
    }

    @Test
    public void getUsersOrderTestNegative() throws Exception {
        mockMvc.perform(get("/api/v1/orders/2").header("Authorization", "Bearer " + token))
                .andExpect(status().is(404));
    }
}
