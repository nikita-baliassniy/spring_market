package ru.geekbrains.cloud.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.cloud.model.Product;
import ru.geekbrains.cloud.repositories.ProductRepository;
import ru.geekbrains.dto.common.ProductDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

//    public Optional<ProductDto> findProductDtoById(Long id) {
//        return productRepository.findById(id).map(ProductDto::new);
//    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(functionProductToDto).collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id).map(functionProductToDto);
    }

    public Function<Product, ProductDto> functionProductToDto = product -> {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setTitle(product.getTitle());
        return productDto;
    };

}
