package com.example.adminuser.model;

public class Customer {

    public String docId;
    public String name;
    public String phone;
    public String email;
    public String password;
    public String address;

    public Customer(){

    }

    public Customer(String docId,String name, String phone, String email, String password, String address) {
        this.docId = docId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
