package com.miageland;

import com.miageland.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MyUtils {

    public static void checkUserRoleGerant(HttpSession session){
        Role role = (Role) session.getAttribute("roleUser");
        if (role == null || role != Role.GERANT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas les droits pour effectuer cette action.");
        }
    }
        public static void checkUserRoleGerantAdministrateur(HttpSession session){
            Role role = (Role) session.getAttribute("roleUser");
            if (role == null || (role != Role.GERANT && role != Role.ADMINISTRATEUR)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas les droits pour effectuer cette action.");
            }
        }

    public static void checkUserRoleVisiteur(HttpSession session){
        Role role = (Role) session.getAttribute("roleUser");
        if (role != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas les droits pour effectuer cette action.");
        }
    }
    public static void checkUserRoleEmploye(HttpSession session){
        Role role = (Role) session.getAttribute("roleUser");
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'avez pas les droits pour effectuer cette action.");
        }
    }
}
