/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mohamed
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    // Cette méthode permet de renvoyer une exception non autorisée lorsque l'on essaye 
    // d'accéder à une ressource sans token
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, 
            AuthenticationException authException) throws IOException, ServletException {
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
    
}
