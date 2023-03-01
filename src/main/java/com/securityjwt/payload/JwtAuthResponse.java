/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.payload;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mohamed
 */
@Getter
@Setter
public class JwtAuthResponse {
    
    private String accessToken;
    
    private String tokenType= "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    
}
