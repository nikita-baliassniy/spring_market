package ru.geekbrains.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.market.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
