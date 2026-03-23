package validation;

import models.Customer;
import models.Product;
import models.Supplier;
import models.Warehouse;

public class Context {
    private Integer productId;
    private Integer supplierId;
    private Integer customerId;
    private Integer warehouseId;
    private Integer quantity;

    private Product product;
    private Supplier supplier;
    private Customer customer;
    private Warehouse warehouse;

    public Integer getProductId() {
        return productId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
