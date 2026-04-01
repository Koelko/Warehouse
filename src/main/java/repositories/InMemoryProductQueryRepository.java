package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryProductQueryRepository implements ProductQueryRepository{
    private ProductRepository productRepository;

    public InMemoryProductQueryRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> findByCategory(String category){
        return productRepository.findAll().stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public List<Product> findByManufacturer(String manufacturer){
        return productRepository.findAll().stream().filter(p -> p.getManufacturer().equalsIgnoreCase(manufacturer)).collect(Collectors.toList());
    }
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findAll().stream().filter(p -> p.getPrice().compareTo(minPrice)>=0).filter(p -> p.getPrice().compareTo(maxPrice)<=0).collect(Collectors.toList());
    }
    public List<Product> findByName(String name){
        return productRepository.findAll().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
    public List<Product> findByFilters(String name, String category, String manufacturer, BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findAll().stream().filter(p -> p != null)
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> manufacturer == null || p.getManufacturer().equalsIgnoreCase(manufacturer))
                .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }
    public Map<String, List<Product>> groupByCategory(){
        return productRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCategory));
    }
}
