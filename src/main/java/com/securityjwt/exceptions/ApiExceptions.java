/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Mohamed
 */
@Getter
public class ApiExceptions extends RuntimeException {

    
    private HttpStatus status;
    private String message;

    public ApiExceptions(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
     
    public ApiExceptions(HttpStatus status, String message, String message1) {
        super(message);
        this.status = status;
        this.message = message;
    }
    
    

  
    
    
}


