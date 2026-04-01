package services;

import models.Product;
import repositories.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductService {
    private ProductRepository productRepository;
    private ProductQueryRepository productQueryRepository;

    public ProductService(ProductRepository productRepository, ProductQueryRepository productQueryRepository) {
        this.productRepository = productRepository;
        this.productQueryRepository = productQueryRepository;
    }
    public Product createProduct(String name, String category, BigDecimal price, String manufacturer){
        return productRepository.createProduct(name, category, price, manufacturer);
    }
    public Product findById(int id){
        return productRepository.findById(id);
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Map<String, List<Product>> getProductGroupedByCategory(){
        return productQueryRepository.groupByCategory();
    }
    public List<Product> findByName(String name) {
        return productQueryRepository.findByName(name);
    }
    public List<Product> findByManufacturer(String manufacturer) {
        return productQueryRepository.findByManufacturer(manufacturer);
    }
    public List<Product> findByCategory(String category){
        return productQueryRepository.findByCategory(category);
    }
}

