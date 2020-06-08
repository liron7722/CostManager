package com.lironprojects.costmanager.Models;

public class ProductsException extends Exception {

    public ProductsException(String message){
        super(message);
    }

    public ProductsException(String message, Throwable cause){
        super(message, cause);
    }
}
