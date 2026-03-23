package validation;

import models.Product;
import repositories.ProductRepository;

public class ProductValidator extends Validator{
    private ProductRepository productRepository;
    public ProductValidator(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    void check(Context context){
        if (context == null){
            throw new IllegalArgumentException("Контекст не может быть пустым");
        }
        Integer productId = context.getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("productId не может быть null");
        }
        Product product = productRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Такого товара нет");
        }
        context.setProduct(product);
    }
}
