package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private int id;
    private LocalDateTime date;
    private Customer customer;
    private List<SaleItem> saleItems;

    public int getId() {
        return id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public Customer getCustomer() {
        return customer;
    }
    public List<SaleItem> getSaleItems() {
        return saleItems;
    }
    public BigDecimal getTotal() {
        return saleItems.stream().map(SaleItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void setDate(LocalDateTime date) {
        if (date == null){
            throw new IllegalArgumentException("Дата должна быть указана");
        }
        this.date = date;
    }

    public void setCustomer(Customer customer) {
        if (customer == null){
            throw new IllegalArgumentException("Покупатель должен быть указан");
        }
        this.customer = customer;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        if (saleItems == null || saleItems.isEmpty()){
            throw new IllegalArgumentException("Товары должены быть указаны");
        }
        this.saleItems = saleItems;
    }

    public Sale(int id, LocalDateTime date, Customer customer, List<SaleItem> saleItems) {
        if (date == null){
            throw new IllegalArgumentException("Дата должна быть указана");
        }
        if (customer == null){
            throw new IllegalArgumentException("Покупатель должен быть указан");
        }
        if (saleItems == null || saleItems.isEmpty()){
            throw new IllegalArgumentException("Товары должены быть указаны");
        }
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.saleItems = saleItems;
    }
}
