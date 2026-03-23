package validation;

import models.Warehouse;
import repositories.WarehouseRepository;

public class WarehouseValidator extends Validator{
    private WarehouseRepository warehouseRepository;
    public WarehouseValidator(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }
    @Override
    void check (Context context){
        if (context == null){
            throw new IllegalArgumentException("Контекст не может быть пустым");
        }
        Integer warehouseId = context.getWarehouseId();
        if (warehouseId == null){
            throw new IllegalArgumentException("warehouseId не может быть пустым");
        }
        Warehouse warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse == null){
            throw new IllegalArgumentException("Такого склада нет");
        }
        context.setWarehouse(warehouse);
    }
}
