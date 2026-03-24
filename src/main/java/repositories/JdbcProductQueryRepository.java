package repositories;

import models.Product;
import models.Supplier;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcProductQueryRepository implements ProductQueryRepository{


    @Override
    public List<Product> findByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findByManufacturer(String manufacturer) {
        return null;
    }

    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public List<Product> findByFilters(String name, String category, String manufacturer, BigDecimal minPrice, BigDecimal maxPrice) {
        return null;
    }

    @Override
    public Map<String, List<Product>> groupByCategory() {
        return null;
    }
}

