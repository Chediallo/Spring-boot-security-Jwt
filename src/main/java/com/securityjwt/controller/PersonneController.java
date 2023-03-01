/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.controller;

import com.securityjwt.entity.Personne;
import com.securityjwt.exceptions.ResourceNotFoundExceptions;
import com.securityjwt.repository.PersonneRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohamed
 */
@RestController
@RequestMapping("api/personnes")
public class PersonneController {

    @Autowired
    private PersonneRepository personneRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Personne> createPersonne( @RequestBody Personne personne) {
        Personne createPersonne = new Personne();
        createPersonne.setPrenom(personne.getPrenom());
        createPersonne.setNom(personne.getNom());
        createPersonne.setAge(personne.getAge());
        return new ResponseEntity<>(personneRepository.save(createPersonne), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {

        List<Personne> personnes = personneRepository.findAll();
        if(personnes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable(name = "id") Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));

        return new ResponseEntity<>(personne, HttpStatus.OK);
    }

    
    @GetMapping("/query")
    public ResponseEntity<List<Personne>> getPersonneByPrenomOrNom(@RequestParam(value = "prenom", required = false) String prenom,
            @RequestParam(value = "nom", required = false) String nom) {

        List<Personne> personnes = new ArrayList<>();

        if (prenom == null && nom == null) {
            personneRepository.findAll().forEach(personnes::add);
        } else {
            personneRepository.findByPrenomOrNom(prenom, nom).forEach(personnes::add);
        }

        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@RequestBody Personne personne,
            @PathVariable(name = "id") Long id) {
        
        Personne updatePersonne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));
        
        updatePersonne.setPrenom(personne.getPrenom());
        updatePersonne.setNom(personne.getNom());
        updatePersonne.setAge(personne.getAge());
        return new ResponseEntity<>(personneRepository.save(updatePersonne), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonne(@PathVariable(name = "id") Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Personne", "id", id));
        personneRepository.delete(personne);
        return new ResponseEntity<>(String.format("Personne with id %s deleted successfully", id), HttpStatus.OK);
    }


}
