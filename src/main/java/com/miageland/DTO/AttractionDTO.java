package com.miageland.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class AttractionDTO {
    @JsonIgnoreProperties
    private String nom;
    @JsonIgnoreProperties
    private boolean estOuverte;
}
