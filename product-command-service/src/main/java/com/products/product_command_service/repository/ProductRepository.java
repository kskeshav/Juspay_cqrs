package com.products.product_command_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.product_command_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    
} 
