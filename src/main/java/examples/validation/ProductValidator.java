package examples.validation;

import models.Product;
import repositories.ProductRepository;

public class ProductValidator extends Validator{
    private ProductRepository productRepository;
    public ProductValidator(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    void check(Context context){
        Product product = productRepository.findById(context.getProductId()).orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
        context.setProduct(product);
    }
}
