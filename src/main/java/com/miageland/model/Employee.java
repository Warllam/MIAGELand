package com.miageland.model;
import jakarta.persistence.*;


@Entity
@Table(name = "Employee")
public class Employee {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String nom;
    private String prenom;
    private String mail;
    private Role role;

    // Constructeur
    public Employee( String nom, String prenom, String mail, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
    }

    // Constructeur
    public Employee() {}

    // Getters and setters
    public long getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

