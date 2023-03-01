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
public class Login {
    
    private String usernameOrEmail;
    private String password;
}
