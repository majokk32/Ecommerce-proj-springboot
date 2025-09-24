package com.ecommerce.sbproject.controller;


import com.ecommerce.sbproject.model.Product;
import com.ecommerce.sbproject.payload.ProductDTO;
import com.ecommerce.sbproject.payload.ProductResponse;
import com.ecommerce.sbproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryID}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @PathVariable Long categoryID){
        ProductDTO productDTO = productService.addProduct(categoryID, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/categories/{categoryID}/product")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("public/categories/{categoryID}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long category){
        ProductResponse productResponse = productService.searchByCategory(category);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
