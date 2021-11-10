package com.example.shoppingservice.service;

import java.util.List;

import com.example.shoppingservice.entity.Invoice;
//import com.example.shoppingservice.repository.InvoiceItemRepository;
import com.example.shoppingservice.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    //@Autowired
    //private InvoiceItemRepository invoiceItemRepository;

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if(invoiceDB != null){
            return invoiceDB;
        }
        invoice.setStatus("CREATED");
        invoiceDB = invoiceRepository.save(invoice);
        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.getById(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.getById(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setStatus("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(int id) {
        return invoiceRepository.findById(id).orElse(null);
    }
    
}
