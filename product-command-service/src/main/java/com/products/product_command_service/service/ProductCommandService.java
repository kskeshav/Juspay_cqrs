package com.products.product_command_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.products.product_command_service.dto.ProductEvent;
import com.products.product_command_service.entity.Product;
import com.products.product_command_service.repository.ProductRepository;

@Service
public class ProductCommandService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Product createProduct(Product product){
        Product productDO = repository.save(product);
        ProductEvent event = new ProductEvent("CreateProduct", productDO);
        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }
    public Product updateProduct(long id, Product product){
        Product existingProduct = repository.findById(id).get();
        existingProduct.setDescription(product.getDescription());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        Product productDO = repository.save(existingProduct);
        ProductEvent event = new ProductEvent("UpdateProduct", productDO);
        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }
}
