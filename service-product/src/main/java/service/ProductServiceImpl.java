package service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import entity.Category;
import entity.Product;
import lombok.AllArgsConstructor;
import repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product creaProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if(productDB == null){
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setStock(product.getStock());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(int id) {
        Product productDB = getProduct(id);
        if(productDB == null){
            return null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(int id, Double quantity) {
        Product productDB = getProduct(id);
        if(productDB == null){
            return null;
        }
        Double stock = productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
    
}
