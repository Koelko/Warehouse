package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository {
    Product createProduct(String name, String category, BigDecimal price, String manufacturer);
    void remove(int id);
    Product findById(int id);
    Product update(Product product);
    List<Product> findAll();
}
