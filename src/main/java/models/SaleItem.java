package models;

import java.math.BigDecimal;

public class SaleItem {
    private int id;
    private Product product;
    private int count;
    private BigDecimal priceAtSale;

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getPriceAtSale() {
        return priceAtSale;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар должен быть указан");
        }
        this.product = product;
    }

    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество должено быть положительным числом");
        }
        this.count = count;
    }

    public void setPriceAtSale(BigDecimal priceAtSale) {
        if (priceAtSale.compareTo(BigDecimal.ZERO) <= 0 || priceAtSale == null) {
            throw new IllegalArgumentException("Цена должена быть положительным числом");
        }
        this.priceAtSale = priceAtSale;
    }

    public SaleItem(int id, Product product, int count, BigDecimal priceAtSale) {
        if (product == null) {
            throw new IllegalArgumentException("Товар должен быть указан");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Количество должено быть положительным числом");
        }
        if (priceAtSale == null || priceAtSale.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должена быть положительным числом");
        }
        this.id = id;
        this.product = product;
        this.count = count;
        this.priceAtSale = priceAtSale;
    }

    public BigDecimal getSubtotal(){
        return priceAtSale.multiply(BigDecimal.valueOf(count));
    }
}
