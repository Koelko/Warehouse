package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductQueryRepository {
    List<Product> findByCategory(String category);
    List<Product> findByManufacturer(String manufacturer);
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByName(String name);
    List<Product> findByFilters(String name, String category, String manufacturer, BigDecimal minPrice, BigDecimal maxPrice);
    Map<String, List<Product>> groupByCategory();
}
