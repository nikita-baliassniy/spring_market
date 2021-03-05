package ru.geekbrains.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.market.dto.ProductDto;
import ru.geekbrains.market.model.Product;
import ru.geekbrains.market.repositories.ProductRepository;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDto::new);
    }

    public List<Product> findAll () {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto saveOrUpdate(ProductDto product) {
        Optional<Product> productToUpdate = productRepository.findById(product.getId());
        Product productToAdd;
        if (product.getId() != null && productToUpdate.isPresent()) {
            productToAdd = productToUpdate.get();
        } else {
            productToAdd = new Product();
        }
        productToAdd.setTitle(product.getTitle());
        productToAdd.setPrice(product.getPrice());
        return new ProductDto(productRepository.save(productToAdd));
    }

    public static final Function<Product, ru.geekbrains.market.soap.products.Product> functionEntityToSoap = se -> {
        ru.geekbrains.market.soap.products.Product product =
                new ru.geekbrains.market.soap.products.Product();
        product.setId(se.getId());
        product.setTitle(se.getTitle());
        product.setPrice(se.getPrice());
        try {
            product.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    se.getCreatedAt().toString()));
            product.setUpdatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    se.getUpdatedAt().toString()));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return product;
    };

    public List<ru.geekbrains.market.soap.products.Product> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public ru.geekbrains.market.soap.products.Product getById(Long id) {
        return productRepository.findById(id).map(functionEntityToSoap).get();
    }

}
