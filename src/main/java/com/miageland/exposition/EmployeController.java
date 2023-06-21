package com.miageland.exposition;

import com.miageland.DTO.EmployeDTO;
import com.miageland.metier.EmployeService;
import com.miageland.model.Employe;
import com.miageland.model.Role;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Contrôleur REST pour gérer les employés.
 */
@RestController
@RequestMapping("/employe")
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    /**
     * Endpoint pour créer un employé.
     *
     * @param employe les informations de l'employé à créer
     * @return la réponse HTTP avec le nouvel employé créé
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Employe> createEmploye(@RequestBody EmployeDTO employe,  HttpSession session) {
        try {
            Employe e = employeService.newEmploye(employe, session);
            return ResponseEntity.status(HttpStatus.CREATED).body(e);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Endpoint pour supprimer un employé spécifié par son identifiant.
     *
     * @param id l'identifiant de l'employé à supprimer
     * @return la réponse HTTP avec le statut OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @CookieValue(value = "user") String gerantEmail) {
        try {
            if (!this.employeService.getEmployeByMail(gerantEmail).getRole().equals(Role.GERANT)){
                throw new IllegalAccessException("Vous devez être gérant pour créer un employé.");
            }
             employeService.deleteEmploye(id);
             return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Endpoint pour mettre à jour un employé spécifié par son identifiant.
     *
     * @param id      l'identifiant de l'employé à mettre à jour
     * @param employe les informations de l'employé mises à jour
     * @return la réponse HTTP avec l'employé mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmployee(@PathVariable Long id, @RequestBody EmployeDTO employe) {
        return ResponseEntity.ok(employeService.updateEmploye(id, employe));
    }

    /**
     * Endpoint pour récupérer la liste de tous les employés.
     *
     * @return la réponse HTTP avec la liste de tous les employés
     */
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployees() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    /**
     * Endpoint pour récupérer un employé spécifié par son identifiant.
     *
     * @param id l'identifiant de l'employé à récupérer
     * @return la réponse HTTP avec l'employé correspondant à l'identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getEmployeById(id));
    }

    /**
     * Endpoint pour connecter un employé en utilisant son adresse e-mail et définir le rôle de la session.
     *
     * @param employeParameter les informations de l'employé pour la connexion
     * @param session          la session HTTP
     * @return la réponse HTTP avec un message de connexion réussie
     */
    @PostMapping("/connexion")
    public ResponseEntity<String> loginEmployeeByMail(@RequestBody EmployeDTO employeParameter, HttpSession session) {
        String mail = employeParameter.getMail();
        Employe employe = this.employeService.getEmployeByMail(mail);
        session.setAttribute("roleUser", employe.getRole());
        return ResponseEntity.ok("Connexion réussie avec l'e-mail : " + mail);
    }

    /**
     * Endpoint pour connecter un employé en utilisant son identifiant et définir le rôle de la session.
     *
     * @param id      l'identifiant de l'employé pour la connexion
     * @param session la session HTTP
     * @return la réponse HTTP avec un message de connexion réussie
     */
    @PostMapping("/connexion/{id}")
    public ResponseEntity<String> loginEmployeeByID(@PathVariable Long id, HttpSession session) {
        Employe employe = this.employeService.getEmployeById(id);
        session.setAttribute("roleUser", employe.getRole());
        return ResponseEntity.ok("Connexion réussie avec l'identifiant : " + id + " de rôle " + employe.getRole());
    }

    /**
     * Endpoint pour récupérer le nom de l'employé spécifié par son identifiant.
     *
     * @param id l'identifiant de l'employé
     * @return la réponse HTTP avec le nom de l'employé correspondant à l'identifiant
     */
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getEmployeeNameById(@PathVariable Long id) {
        Employe employe = employeService.getEmployeById(id);
        if (employe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employe.getNom());
    }
}
