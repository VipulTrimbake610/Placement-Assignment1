package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Service;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Repository.CustomerRepository;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    CustomerRepository customerRepository;

    public void addCustomer(Customer customer) throws Exception{
            Customer customer1 = customerRepository.findCustomerByEmail(customer.getEmail());
            if(customer1 == null){
                customerRepository.save(customer);
            }else{
                throw new Exception("Error : Customer Already Exist!");
            }
    }

    public List<Customer> getCustomerList() throws Exception{
       return customerRepository.findAll();
    }

    public List<Customer> getCustomerListUsingToken(String token) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Set content type as needed
        headers.set("Authorization", "Bearer "+ token); // Set your authorization token

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List<Customer>> response = restTemplate.exchange(uri, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Customer>>() {});
        List<Customer> result = response.getBody();

//        Storing all the list of customers got from sunbase into the db
        customerRepository.saveAll(result);

//        returing all the data present in the db.
      return customerRepository.findAll();
    }


    public void updateCustomer(Customer customer) throws Exception{
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getUuid());
        if(optionalCustomer.isEmpty()){
            throw new Exception("Error : Customer Doesn't Exist!");
        }else{
            customerRepository.save(customer);
        }
    }

    public void deleteCustomer(String id)throws Exception{
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isEmpty()){
            throw new Exception("Error : Customer Doesn't Exist!");
        }else{
            customerRepository.deleteById(id);
        }
    }
}
