package com.spring.ecommerce.service;

import com.spring.ecommerce.model.Category;
import com.spring.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    public void addProduct(Product product);

    public List<Product> getAllProduct();

    public Optional<Product> getProductById(Long id);

    public void updateProduct(Product product);

    public void deleteProduct(Long id);

    public List<Product> getAllProductByCategoryId(Long id);
}
