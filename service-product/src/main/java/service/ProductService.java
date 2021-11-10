package service;

import java.util.List;

import entity.Category;
import entity.Product;

public interface ProductService{
    public List<Product> listAllProduct();
    public Product getProduct(int id);
    public Product creaProduct (Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(int id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(int id, Double quantity);

}
