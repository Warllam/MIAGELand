package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miageland.model.Role;
import lombok.Getter;

@Getter
public class EmployeeDTO {
    @JsonIgnoreProperties
    private int id;
    @JsonIgnoreProperties
    private String nom;
    @JsonIgnoreProperties
    private String prenom;
    @JsonIgnoreProperties
    private String mail;
    @JsonIgnoreProperties
    private Role role;

    /*public EmployeeDTO(int id, String nom, String prenom, String mail, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
    }*/
}
