/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.payload;

import lombok.Data;

/**
 *
 * @author Mohamed
 */
@Data
public class SignUp {
    
    private String prenom;
    
    private String username;
    
    private String email;
    
    private String password;
}
