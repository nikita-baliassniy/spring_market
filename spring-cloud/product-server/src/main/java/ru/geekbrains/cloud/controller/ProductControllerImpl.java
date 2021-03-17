package ru.geekbrains.cloud.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.cloud.exceptions.ResourceNotFoundException;
import ru.geekbrains.cloud.services.ProductService;
import ru.geekbrains.dto.common.ProductDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    // http://localhost:8189/market/products?min_price=30&max_price=400
    @Override
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.findAll();
    }

    @Override
    @GetMapping("products/{id}")
    public ProductDto getProductById(@PathVariable(value = "id") String id) {
        return productService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " doens't exist"));
    }

}
