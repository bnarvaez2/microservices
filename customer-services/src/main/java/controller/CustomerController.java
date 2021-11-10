package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import entity.Customer;
import entity.Region;
import lombok.extern.slf4j.Slf4j;
import service.CustomerService;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomer(@RequestParam(name = "regionid", required = false) Integer regionid){
        List<Customer> customers = new ArrayList<>();
        if(regionid == null){
            customers = customerService.findCustomerAll();
            if(customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            customers = customerService.findCustomersByRegion(Region.builder().id(regionid).build());
            if(customers == null){
                log.error("Customers with Region id {} not found.", regionid);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(name = "id") int id){
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer newCustomer = customerService.createCustomer(customer);
        return  ResponseEntity.status( HttpStatus.CREATED).body(newCustomer);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") int id, @RequestBody Customer customer){
        log.info("Updating Customer with id {}", id);
        Customer currentCustomer = customerService.getCustomer(id);
        if (currentCustomer == null) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") int id){
        log.info("Updating Customer with id {}", id);
        Customer currentCustomer = customerService.getCustomer(id);
        if (currentCustomer == null) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        currentCustomer = customerService.deleteCustomer(currentCustomer);
        return ResponseEntity.ok(currentCustomer);
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
