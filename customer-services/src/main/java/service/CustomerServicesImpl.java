package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Customer;
import entity.Region;
import repository.CustomerRepository;

@Service
public class CustomerServicesImpl implements CustomerService{
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB != null){
            return customerDB;
        }
        customer.setStatus("CREATED");
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB == null){
            return null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB == null){
            return null;
        }
        customerDB.setStatus("DELETED");
        return customerRepository.save(customerDB);
    }
}
