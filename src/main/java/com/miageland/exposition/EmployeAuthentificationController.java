package com.miageland.exposition;

import com.miageland.metier.EmployeService;
import com.miageland.model.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class EmployeAuthentificationController {
    @Autowired
    private EmployeService employeService;

    /**
     *
     * @param authenticationRequest
     * @return authentification ok ou non
     */
    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody Employe authenticationRequest) {

        //  existe?
        boolean exists = employeService.existEmployeByEmail(authenticationRequest.getMail());

        if (exists) {
            // L'employé existe
            return ResponseEntity.ok("Authentification réussie");
        } else {
            // Employé non trouvé
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'authentification a échoué, il y a peut etre une erreur dans l'adresse mail");
        }
    }
}
