package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Repository;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    public Customer findCustomerByEmail(String Email);

    @Query(value = "SELECT * FROM Customer where first_name like concat('%',:searchText,'%') order by first_name",nativeQuery = true)
    public Page<Customer> searchCustomerByName(@Param("searchText") String searchText, Pageable pageable);

    @Query(value = "SELECT * FROM Customer where city like concat('%',:searchText,'%') order by city asc",nativeQuery = true)
    public Page<Customer> searchCustomerByCity(@Param("searchText") String searchText, Pageable pageable);

    @Query(value = "SELECT * FROM Customer where email like concat('%',:searchText,'%') order by email asc",nativeQuery = true)
    public Page<Customer> searchCustomerByEmail(@Param("searchText") String searchText, Pageable pageable);

    @Query(value = "SELECT * FROM Customer where phone like concat('%',:searchText,'%') order by phone asc",nativeQuery = true)
    public Page<Customer> searchCustomerByPhone(@Param("searchText") String searchText, Pageable pageable);
}
