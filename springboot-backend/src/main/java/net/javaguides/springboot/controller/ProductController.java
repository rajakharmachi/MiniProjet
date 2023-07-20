package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // create product rest api
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // get product by id rest api
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));
        return ResponseEntity.ok(product);
    }

    // update product rest api
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));

        product.setProductName(productDetails.getProductName());
        product.setUnitPrice(productDetails.getUnitPrice());
        product.setQuantity(productDetails.getQuantity());

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // delete product rest api
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
