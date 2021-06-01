package ru.geekbrains.market.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllProductsMaxPriceTest() throws Exception {
        double price = 100;
        mvc.perform(get("/api/v1/products?max_price=" + price)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].price", lessThan(price)));
    }

    @Test
    public void getAllProductsMinPriceTest() throws Exception {
        double price = 100;
        mvc.perform(get("/api/v1/products?min_price=" + price)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].price", greaterThan(price)));
    }

    @Test
    public void getAllProductsMinMaxPriceTest() throws Exception {
        double maxPrice = 100;
        double minPrice = 0;
        mvc.perform(get("/api/v1/products?min_price=" + minPrice + "&max_price=" + maxPrice)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].price", greaterThan(minPrice)))
                .andExpect(jsonPath("$.content[0].price", lessThan((maxPrice))));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        mvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].title", is("milk")));
    }
}
