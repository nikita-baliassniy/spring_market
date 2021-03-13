package ru.geekbrains.cloud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.cloud.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
