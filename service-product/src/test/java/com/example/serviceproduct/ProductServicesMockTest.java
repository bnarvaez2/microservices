package com.example.serviceproduct;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import entity.Category;
import entity.Product;
import repository.ProductRepository;
import service.ProductService;
import service.ProductServiceImpl;

@SpringBootTest
public class ProductServicesMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productServices;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productServices = new ProductServiceImpl(productRepository);    
        Product computer = Product.builder()
                .id(1)
                .name("computer")
                .category(Category.builder().id(1).build())
                .description("")
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("12.5"))
                .build();

        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer))
                .thenReturn(computer);          
    }

    @Test
    public void whenValidID_thenReturnProduct(){
        Product found = productServices.getProduct(1);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock(){
        Product found = productServices.updateStock(1, Double.parseDouble("8"));
        Assertions.assertThat(found.getStock()).isEqualTo(13);
    }

}
