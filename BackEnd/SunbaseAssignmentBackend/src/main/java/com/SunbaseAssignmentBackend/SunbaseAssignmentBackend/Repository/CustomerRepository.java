package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Repository;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    public Customer findCustomerByEmail(String Email);
}
