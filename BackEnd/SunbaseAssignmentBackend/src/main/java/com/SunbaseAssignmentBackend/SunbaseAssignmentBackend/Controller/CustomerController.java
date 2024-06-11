package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Controller;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Service.CustomerService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") // Specify the allowed origin
@RequestMapping("customer")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
//    Backend should have API for:
//- Create a customer
//- Update a customer
//- Get a list of customer (API with pagination sorting and searching )
//- Get a single customer based on ID
//- Delete a customer

    @Autowired
    CustomerService customerService;

    // Adding Customer to the db
    @PostMapping("add-customer")
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        try{
            customerService.addCustomer(customer);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // This api will return list of products.
    // pagination, Sorting and Searching implemented in the Service layer of this Api.
    @GetMapping("get-customer-list/{page}/{size}")
    public Page<Customer> getCustomerList(@PathVariable int page, @PathVariable int size, @RequestParam String searchText, @RequestParam String searchBy){
        try{
            Page<Customer> customerList = customerService.getCustomerList(page,size,searchText,searchBy);
            return customerList;
        }catch (Exception e){
            return null;
        }
    }

//    Remote Backend Api has implemented in this function.
//    This api will call sunbase api to get the data. and then store the data into the database.
//    if any data is already present in the db it will get updated otherwise will get added.
//    Note : Data is getting checked wheer its  present or not in the db based on email address.
    @GetMapping("get-customer-list-with-token")
    public Page<Customer> getCustomerListUsingToken(@RequestHeader("access_token") String token){
        try{
            return customerService.getCustomerListUsingToken(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Simple Update API will check if the customer is present it will update.
    // otherwise throw an customerDoent'tExist exception.
    @PutMapping("update-customer")
    public ResponseEntity updateCustomer(@RequestBody Customer customer){
        try{
            customerService.updateCustomer(customer);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // Simple delete API will check if the customer is present it will delete.
    // otherwise throw an customerDoent'tExist exception.
    @DeleteMapping("delete-customer")
    public ResponseEntity deleteCustomer(@RequestParam String id){
        try{
            customerService.deleteCustomer(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
