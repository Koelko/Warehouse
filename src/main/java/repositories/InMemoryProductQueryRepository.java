package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryProductQueryRepository implements ProductQueryRepository{
    private InMemoryProductRepository inMemoryProductRepository;

    public InMemoryProductQueryRepository(InMemoryProductRepository inMemoryProductRepository) {
        this.inMemoryProductRepository = inMemoryProductRepository;
    }
    public List<Product> findByCategory(String category){
        return inMemoryProductRepository.findAll().stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
    public List<Product> findByManufacturer(String manufacturer){
        return inMemoryProductRepository.findAll().stream().filter(p -> p.getManufacturer().equalsIgnoreCase(manufacturer)).collect(Collectors.toList());
    }
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice){
        return inMemoryProductRepository.findAll().stream().filter(p -> p.getPrice().compareTo(minPrice)>=0).filter(p -> p.getPrice().compareTo(maxPrice)<=0).collect(Collectors.toList());
    }
    public List<Product> findByName(String name){
        return inMemoryProductRepository.findAll().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
    public List<Product> findByFilters(String name, String category, String manufacturer, BigDecimal minPrice, BigDecimal maxPrice){
        return inMemoryProductRepository.findAll().stream().filter(p -> p != null)
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> manufacturer == null || p.getManufacturer().equalsIgnoreCase(manufacturer))
                .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }
    public Map<String, List<Product>> groupByCategory(){
        return inMemoryProductRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCategory));
    }
}
