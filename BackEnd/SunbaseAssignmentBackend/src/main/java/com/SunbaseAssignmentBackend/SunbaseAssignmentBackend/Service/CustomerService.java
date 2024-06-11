package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Service;

import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models.Customer;
import com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    public Page<Customer> getCustomerList(int page, int size, String searchText, String searchBy) throws Exception{
        Pageable pageable = PageRequest.of(page,size);
        if (searchBy.equals("first_name")){
               return customerRepository.searchCustomerByName(searchText,pageable);
       }else if (searchBy.equals("city")){
           return customerRepository.searchCustomerByCity(searchText,pageable);
       }else if (searchBy.equals("email")){
           return customerRepository.searchCustomerByEmail(searchText,pageable);
       }else if (searchBy.equals("phone")){
           return customerRepository.searchCustomerByPhone(searchText,pageable);
       }else{
           return customerRepository.findAll(PageRequest.of(page,size));
       }
    }

    public Page<Customer> getCustomerListUsingToken(String token) throws Exception{

        // Here the remote api is getting called.
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List<Customer>> response = restTemplate.exchange(uri, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Customer>>() {});
        List<Customer> result = response.getBody();

//        Storing all the list of customers got from sunbase via the Token into the db
//        If Email exist then data will get updated otherwise new record will be added.
//        Also validating the email like
//        1. Email should not be null
//        2. Email should end with @gmail.com

        for(Customer customer : result){
            String custMail = customer.getEmail();
            Customer customer1 = customerRepository.findCustomerByEmail(custMail);

            if(customer1 == null && customer.getEmail().endsWith("@gmail.com")){
                customerRepository.save(customer);
            }else if(customer1 != null){
                customer1.setFirst_name(customer.getFirst_name());
                customer1.setLast_name(customer.getLast_name());
                customer1.setStreet(customer.getStreet());
                customer1.setAddress(customer.getAddress());
                customer1.setCity(customer.getCity());
                customer1.setState(customer.getState());
                customer1.setPhone(customer.getPhone());
                customerRepository.save(customer1);
            }
        }

//        returing all the data present in the db.
      return customerRepository.findAll(PageRequest.of(0,5));
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
