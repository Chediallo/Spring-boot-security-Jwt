/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Mohamed
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptions extends RuntimeException{
    
    private String nomRessource;
    private String nomChamp;
    private Long valeurChamp;

    public ResourceNotFoundExceptions(String nomRessource, String nomChamp, Long valeurChamp) {
        super(String.format("%s with %s : %s not found in this API  " ,nomRessource, nomChamp, valeurChamp));
        this.nomRessource = nomRessource;
        this.nomChamp = nomChamp;
        this.valeurChamp = valeurChamp;
    }
    
    
}
