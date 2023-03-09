package com.example.otasmeservice.exception;

public class InvoiceAlreadyExists extends RuntimeException{
    public InvoiceAlreadyExists(String message){
        super(message);
    }
}
