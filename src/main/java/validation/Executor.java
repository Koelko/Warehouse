package validation;

import models.*;
import repositories.SaleRepository;
import repositories.StockRepository;

public class Executor extends Validator{
    private StockRepository stockRepository;
    private SaleRepository saleRepository;
    public Executor (StockRepository stockRepository, SaleRepository saleRepository){
        this.saleRepository = saleRepository;
        this.stockRepository = stockRepository;
    }
    @Override
    void check (Context context){
        Product product = context.getProduct();
        Warehouse warehouse = context.getWarehouse();
        Integer count = context.getQuantity();
        if (context.getSupplier() != null){
            Supplier supplier = context.getSupplier();
            for (StockItem stockItem : stockRepository.findAll()){
                if (stockItem.getProduct().getId() == product.getId() && stockItem.getSupplier().getId() == supplier.getId() && stockItem.getWarehouse().getId() == warehouse.getId()){
                    stockItem.setCount(stockItem.getCount() + count);
                    return;
                }
            }
            stockRepository.createStockItem(count, product, supplier, warehouse);
        }
        else if (context.getCustomer() != null){
            Customer customer = context.getCustomer();

        } else {
            throw new IllegalArgumentException("Неизвестная опурация");
        }
    }
}
