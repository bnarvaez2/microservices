package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Customer;
import entity.Region;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}
