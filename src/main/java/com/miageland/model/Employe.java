package com.miageland.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "Employe")
public class Employe {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String nom;
    private String prenom;
    private String mail;
    private Role role;

    // Constructeur
    public Employe(String nom, String prenom, String mail, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
    }

    // Constructeur
    public Employe() {}

}

