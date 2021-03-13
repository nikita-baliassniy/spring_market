package ru.geekbrains.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.dto.common.ProductDto;

import java.util.List;

public interface ProductController {
    @GetMapping("/products")
    List<ProductDto> getAllProducts();

    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable(value = "id") String id);

}
