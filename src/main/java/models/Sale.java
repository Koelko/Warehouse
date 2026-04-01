package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private int id;
    private LocalDateTime date;
    private int customerId;
    private List<SaleItem> saleItems;

    public int getId() {
        return id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public int getCustomerId() {
        return customerId;
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

    public void setCustomer(int customerId) {
        this.customerId = customerId;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        if (saleItems == null || saleItems.isEmpty()){
            throw new IllegalArgumentException("Товары должены быть указаны");
        }
        this.saleItems = saleItems;
    }

    public Sale(int id, LocalDateTime date, int customerId, List<SaleItem> saleItems) {
        if (date == null){
            throw new IllegalArgumentException("Дата должна быть указана");
        }
        if (saleItems == null || saleItems.isEmpty()){
            throw new IllegalArgumentException("Товары должены быть указаны");
        }
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.saleItems = saleItems;
    }
}
