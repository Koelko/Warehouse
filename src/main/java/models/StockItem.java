package models;

public class StockItem {
    private int id;
    private int count;
    private int productId;
    private int supplierId;
    private int warehouseId;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getProductId() {
        return productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }
    public void setCount(int count) {
        if (count > 0){
            this.count = count;
        } else{
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
    }

    public void setProductId(int productId) {
        if (productId > 0) {
            this.productId = productId;
        } else{
            throw new IllegalArgumentException("Id не может быть отрицательным");
        }
    }

    public void setSupplierId(int supplierId) {
        if (supplierId > 0) {
            this.supplierId = supplierId;
        } else{
            throw new IllegalArgumentException("Id не может быть отрицательным");
        }
    }

    public void setWarehouseId(int warehouse) {
        if (warehouse <= 0){
            throw new IllegalArgumentException("Id не может быть отрицательным");
        }
        this.warehouseId = warehouseId;
    }

    public StockItem(int id, int count, int productId, int supplierId, int warehouseId) {
        if(count <= 0){
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        if (productId <= 0){
            throw new IllegalArgumentException("Продукт должен быть");
        }
        if (supplierId <= 0){
            throw new IllegalArgumentException("Поставщик должен быть");
        }
        if (warehouseId <= 0){
            throw new IllegalArgumentException("Склад должен быть");
        }
        this.id = id;
        this.count = count;
        this.productId = productId;
        this.supplierId = supplierId;
        this.warehouseId = warehouseId;
    }
}
