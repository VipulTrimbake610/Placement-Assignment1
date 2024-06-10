package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Controller;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") // Specify the allowed origin
@RequestMapping("customer")
public class CustomerController {
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

    @GetMapping("get-customer-list")
    public ResponseEntity getCustomerList(){
        try{
            List<Customer> customerList = customerService.getCustomerList();
            return new ResponseEntity(customerList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-customer-list-with-token")
    public List<Customer> getCustomerListUsingToken(@RequestHeader("access_token") String token){
        System.out.println("Hello");
        System.out.println("token"+token);
        try{
            return customerService.getCustomerListUsingToken(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
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
