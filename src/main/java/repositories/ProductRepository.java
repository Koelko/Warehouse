package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product createProduct(String name, String category, BigDecimal price, String manufacturer);
    Optional<Product> findById(int id);
    Product update(Product product);
    List<Product> findAll();
}
