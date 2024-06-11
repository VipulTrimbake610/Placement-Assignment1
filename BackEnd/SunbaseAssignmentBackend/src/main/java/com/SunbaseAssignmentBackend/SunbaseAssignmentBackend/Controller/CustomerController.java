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

    @PostMapping("add-customer")
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        try{
            customerService.addCustomer(customer);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-customer-list/{page}/{size}")
    public Page<Customer> getCustomerList(@PathVariable int page, @PathVariable int size, @RequestParam String searchText, @RequestParam String searchBy){
        try{
            Page<Customer> customerList = customerService.getCustomerList(page,size,searchText,searchBy);
            return customerList;
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("get-customer-list-with-token")
    public Page<Customer> getCustomerListUsingToken(@RequestHeader("access_token") String token){
        try{
            return customerService.getCustomerListUsingToken(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @PutMapping("update-customer")
    public ResponseEntity updateCustomer(@RequestBody Customer customer){
        try{
            customerService.updateCustomer(customer);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("delete-customer")
    public ResponseEntity deleteCustomer(@RequestParam String id){
        try{
            customerService.deleteCustomer(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//
//    @GetMapping("get-customer")
//

}
