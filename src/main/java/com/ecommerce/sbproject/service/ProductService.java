package com.ecommerce.sbproject.service;


import com.ecommerce.sbproject.model.Product;
import com.ecommerce.sbproject.payload.ProductDTO;
import com.ecommerce.sbproject.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryID, Product product);
    ProductResponse getAllProducts();
    ProductResponse searchByCategory(Long categoryID);
}
