package repositories;

import models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryProductRepository implements ProductRepository{
    private List<Product> products = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    public Product createProduct(String name, String category, BigDecimal price, String manufacturer){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой");
        }
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException("Производитель не может быть пустым");
        }
        Product product =  new Product(nextId++, name, category, price, manufacturer);
        products.add(product);
        return product;
    }
    public void remove(int id){
        products.removeIf(product -> product.getId() == id);
    }
    public Product findById(int id){
        for (Product product : products) {
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }
    public Product update(Product product){
        for (int i = 0; i < products.size(); i++){
            if (products.get(i).getId() == product.getId()){
                products.set(i, product);
                return product;
            }
        }
        throw new IllegalArgumentException("Продукт с id \" + product.getId() + \" не найден");
    }
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

}
