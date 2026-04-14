package examples.validation;

import models.Warehouse;
import repositories.WarehouseRepository;

public class WarehouseValidator extends Validator{
    private WarehouseRepository warehouseRepository;
    public WarehouseValidator(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }
    @Override
    void check (Context context){
        Warehouse warehouse = warehouseRepository.findById(context.getWarehouseId()).orElseThrow(() -> new IllegalArgumentException("Склад не найден"));
        context.setWarehouse(warehouse);
    }
}
