package models;

import java.math.BigDecimal;

public class SaleItem {
    private int id;
    private int productId;
    private int count;
    private BigDecimal priceAtSale;
    private int warehouseId;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getPriceAtSale() {
        return priceAtSale;
    }
    public int getWarehouseId() {return warehouseId;}

    public void setProductId(int productId) {
        this.productId = productId;
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
    public void setWarehouseId(int warehouseId){
        this.warehouseId = warehouseId;
    }

    public SaleItem(int id, int productId, int count, BigDecimal priceAtSale, int warehouseId) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество должено быть положительным числом");
        }
        if (priceAtSale == null || priceAtSale.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должена быть положительным числом");
        }
        this.id = id;
        this.productId = productId;
        this.count = count;
        this.priceAtSale = priceAtSale;
        this.warehouseId = warehouseId;
    }

    public BigDecimal getSubtotal(){
        return priceAtSale.multiply(BigDecimal.valueOf(count));
    }
}
