package com.example.shoppingservice.repository;

import com.example.shoppingservice.entity.InvoiceItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {
}
