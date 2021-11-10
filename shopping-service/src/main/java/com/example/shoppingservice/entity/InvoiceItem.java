package com.example.shoppingservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_invoce_items")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Positive(message = "La cantidad debe ser mayor que cero")
    private int quantity;
    @Positive(message = "El precio debe ser mayor que cero")
    private Double  price;

    @Column(name = "product_id")
    private int productId;


    @Transient
    private Double subTotal;

    //@Transient
    //private Product product;

    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }
    public InvoiceItem(){
        this.quantity=(int) 0;
        this.price=(double) 0;

    }
}
