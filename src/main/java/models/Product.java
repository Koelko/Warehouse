package models;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String category;
    private BigDecimal price;
    private String manufacturer;

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public  String getManufacturer() {return manufacturer;}

    public void setName(String name) {

        if (name != null && !name.isEmpty()){
            this.name = name;
        }else {
            throw new IllegalArgumentException("Название должно быть  заполнено");
        }
    }
    public void setCategory(String category) {
        if (category != null && !category.isEmpty()){
            this.category = category;
        }else {
            throw new IllegalArgumentException("Категория должна быть заполнена");
        }
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
            this.price = price;
        } else{
            throw new IllegalArgumentException("Цена должна быть положительным числом");
        }
    }
    public void setManufacturer(String manufacturer){
        if (manufacturer != null && !manufacturer.isEmpty()){
            this.manufacturer = manufacturer;
        } else{
            throw new IllegalArgumentException("Производитель должен быть указан");
        }
    }

    public Product(int id, String name, String category, BigDecimal price, String manufacturer) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть положительным числом");
        }
        if (manufacturer == null || manufacturer.isEmpty()) {
            throw new IllegalArgumentException("Производитель должен быть указан");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.manufacturer = manufacturer;
    }
}
