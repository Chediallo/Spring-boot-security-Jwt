/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.jwt;

import com.securityjwt.security.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Mohamed
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injecter les dépendances nécessaires
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
            
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        
        // Recupérer le token JWT depuis la requete http
        String token = getJWTFromRequest(request);
        
        // Valider le token
        if(StringUtils.hasText(token)&& jwtTokenProvider.validateToken(token)){
            // Recuperer le username depuis le token
            String username = jwtTokenProvider.getUsernameFromJWT(token);
            
            // Charger l'utilisateur associé au token JWT depuis la base de donnée
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,null,userDetails.getAuthorities());
            
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            // Mettre les informations dans le Security Context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        
        filterChain.doFilter(request, response);

    }
    
    // Bearer <accessToken>
    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        
        return null;
    }
    
}
