package models;

public class StockItem {
    private int id;
    private int count;
    private Product product;
    private Supplier supplier;
    private Warehouse warehouse;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
    public void setCount(int count) {
        if (count > 0){
            this.count = count;
        } else{
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
    }

    public void setProduct(Product product) {
        if (product != null) {
            this.product = product;
        } else{
            throw new IllegalArgumentException("Продукт должен быть");
        }
    }

    public void setSupplier(Supplier supplier) {
        if (supplier != null) {
            this.supplier = supplier;
        } else{
            throw new IllegalArgumentException("Поставщик должен быть");
        }
    }

    public void setWarehouse(Warehouse warehouse) {
        if (warehouse == null){
            throw new IllegalArgumentException("Склад должен быть");
        }
        this.warehouse = warehouse;
    }

    public StockItem(int id, int count, Product product, Supplier supplier, Warehouse warehouse) {
        if(count < 0){
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        if (product == null){
            throw new IllegalArgumentException("Продукт должен быть");
        }
        if (supplier == null){
            throw new IllegalArgumentException("Поставщик должен быть");
        }
        if (warehouse == null){
            throw new IllegalArgumentException("Склад должен быть");
        }
        this.id = id;
        this.count = count;
        this.product = product;
        this.supplier = supplier;
        this.warehouse = warehouse;
    }
}
