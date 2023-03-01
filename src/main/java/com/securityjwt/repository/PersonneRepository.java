/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.securityjwt.repository;

import com.securityjwt.entity.Personne;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mohamed
 */
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    List<Personne> findByPrenomOrNom(String prenom, String nom);

}
