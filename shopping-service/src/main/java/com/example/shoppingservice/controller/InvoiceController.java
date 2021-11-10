package com.example.shoppingservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.shoppingservice.entity.Invoice;
import com.example.shoppingservice.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> listAllInvoice(){
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        if(invoices.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable(name = "id") int id){
        log.info("Fetching Invoice with id {}", id);
        Invoice invoice = invoiceService.getInvoice(id);
        if(invoice == null){
            log.error("Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result){
        log.info("Creating Invoice : {}", invoice);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Invoice newInvoice = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable(value = "id") int id, @RequestBody Invoice invoice){
        log.info("Updating Invoice with id {}", id);
        Invoice currentInvoice = invoiceService.getInvoice(id);
        if(currentInvoice == null){
            log.error("Unable to update. Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        invoice.setId(id);
        currentInvoice = invoiceService.updateInvoice(invoice);
        return ResponseEntity.ok(currentInvoice);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable(value = "id") int id){
        log.info("Deleting Invoice with id {}", id);
        Invoice currentInvoice = invoiceService.getInvoice(id);
        if(currentInvoice == null){
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        currentInvoice = invoiceService.deleteInvoice(currentInvoice);
        return ResponseEntity.ok(currentInvoice);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errosList = result.getFieldErrors().stream()
            .map(err -> {
                Map<String, String> error = new HashMap<>();
                error.put(err.getField(), err.getDefaultMessage());
                return error;
            }).collect(Collectors.toList());
        
        ErrorMessage errorMessage = ErrorMessage.builder()
            .code("01")
            .messages(errosList)
            .build();    
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
