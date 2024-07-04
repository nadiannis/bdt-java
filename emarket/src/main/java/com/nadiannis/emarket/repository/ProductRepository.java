package com.nadiannis.emarket.repository;

import com.nadiannis.emarket.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPrice(double price, Pageable pageable);

}
