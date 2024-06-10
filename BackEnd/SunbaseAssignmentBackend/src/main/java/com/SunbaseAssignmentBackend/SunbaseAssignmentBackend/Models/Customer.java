package com.SunbaseAssignmentBackend.SunbaseAssignmentBackend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String uuid;
    String first_name;
    String last_name;
    String street;
    String address;
    String city;
    String state;
    String email;
    String phone;

//Demo Model
//  "first_name": "Jane",
//  "last_name": "Doe",
//  "street": "Elvnu Street",
//  "address": "H no 2 ",
//  "city": "Delhi",
//  "state": "Delhi",
//  "email": "sam@gmail.com",
//  "phone": "12345678

}
