package examples.validation;
import models.Supplier;
import repositories.SupplierRepository;
public class SupplierValidator extends Validator{
    private SupplierRepository supplierRepository;
    public SupplierValidator(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }
    @Override
    void check(Context context){
        Supplier supplier = supplierRepository.findById(context.getSupplierId()).orElseThrow(() -> new IllegalArgumentException("Поставщик не найден"));
        context.setSupplier(supplier);
    }
}
