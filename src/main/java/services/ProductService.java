package services;

import models.Product;
import repositories.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductService {
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemoryProductQueryRepository inMemoryProductQueryRepository;

    public ProductService(InMemoryProductRepository inMemoryProductRepository, InMemoryProductQueryRepository inMemoryProductQueryRepository) {
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryProductQueryRepository = inMemoryProductQueryRepository;
    }
    public Product createProduct(String name, String category, BigDecimal price, String manufacturer){
        return inMemoryProductRepository.createProduct(name, category, price, manufacturer);
    }
    public Product findById(int id){
        return inMemoryProductRepository.findById(id);
    }
    public List<Product> findAll(){
        return inMemoryProductRepository.findAll();
    }
    public void remove(int id){
        inMemoryProductRepository.remove(id);
    }
    public Map<String, List<Product>> getProductGroupedByCategory(){
        return inMemoryProductQueryRepository.groupByCategory();
    }
    public List<Product> findByName(String name) {
        return inMemoryProductQueryRepository.findByName(name);
    }
    public List<Product> findByManufacturer(String manufacturer) {
        return inMemoryProductQueryRepository.findByManufacturer(manufacturer);
    }
    public List<Product> findByCategory(String category){
        return inMemoryProductQueryRepository.findByCategory(category);
    }



}

