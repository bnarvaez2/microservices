package com.example.shoppingservice.repository;

import java.util.List;

import com.example.shoppingservice.entity.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    public List<Invoice> findByCustomerId(int customerId );
    public Invoice findByNumberInvoice(String numberInvoice);    
}
