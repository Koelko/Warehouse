package validation;
import models.Supplier;
import repositories.SupplierRepository;
public class SupplierValidator extends Validator{
    private SupplierRepository supplierRepository;
    public SupplierValidator(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }
    @Override
    void check(Context context){
        if (context == null){
            throw new IllegalArgumentException("Контекст не может быть пустым");
        }
        Integer supplierId = context.getSupplierId();
        if (supplierId == null){
            throw new IllegalArgumentException("supplierId не может быть null");
        }
        Supplier supplier = supplierRepository.findById(supplierId);
        if (supplier == null){
            throw new IllegalArgumentException("Такого поставщика нет");
        }
        context.setSupplier(supplier);
    }
}
