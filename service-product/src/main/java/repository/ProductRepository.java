package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Category;
import entity.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {

    public List<Product> findByCategory(Category category);
}
