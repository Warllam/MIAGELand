package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miageland.model.Role;
import lombok.Getter;

@Getter
public class EmployeDTO {
    @JsonIgnoreProperties
    private int id;
    @JsonIgnoreProperties
    private String nom;
    @JsonIgnoreProperties
    private String prenom;
    @JsonIgnoreProperties
    private String mail;
    @JsonIgnoreProperties
    private String role;


}
