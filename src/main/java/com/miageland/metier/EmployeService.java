package com.miageland.metier;

import com.miageland.DAO.EmployeRepository;
import com.miageland.DTO.EmployeDTO;
import com.miageland.model.Employe;
import com.miageland.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;
    public EmployeService() {

    }

    /**
     *
     * @return tous les employes
     */
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    /**
     * employe en fonction de leur id
     * @param id
     * @return l'employe
     */
    public Employe getEmployeById(int id) {
        Optional<Employe> employee = employeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RuntimeException("Employe non trouvé avec l'id " + id);
        }
    }

    /**
     * création d'un employee
     * @param newEmployee
     * @return l'employee créé et sauvegardé
     */
    public Employe newEmploye(EmployeDTO newEmployee) {
        Employe e;
        switch (newEmployee.getRole()) {
            case "ADMINISTRATEUR":
                e = new Employe(newEmployee.getNom(), newEmployee.getPrenom(), newEmployee.getMail(), Role.ADMINISTRATEUR);
                break;
            case "GERANT":
                e = new Employe(newEmployee.getNom(), newEmployee.getPrenom(), newEmployee.getMail(), Role.GERANT);
                break;
            //par defaut on attribue le role d'employee
            default:
                e = new Employe(newEmployee.getNom(), newEmployee.getPrenom(), newEmployee.getMail(), Role.EMPLOYEE);
                break;
        }
        this.employeRepository.save(e);
        return e;
        //return "Création ok";
    }



    /**
     * modif informations d'un employee grace a son id
     * @param id
     * @param updatedEmploye
     * @return l'employee modifié
     */
    public Employe updateEmploye(int id, EmployeDTO updatedEmploye) {
        Optional<Employe> employee = employeRepository.findById(id);

        if (employee.isPresent()) {
            Employe existingEmploye = employee.get();
            existingEmploye.setPrenom(updatedEmploye.getPrenom());
            existingEmploye.setNom(updatedEmploye.getNom());
            existingEmploye.setMail(updatedEmploye.getMail());
            switch (updatedEmploye.getRole()) {
            case "ADMINISTRATEUR":
                existingEmploye.setRole(Role.ADMINISTRATEUR);
                break;
            case "GERANT":
                existingEmploye.setRole(Role.GERANT);
                break;
            //par defaut on attribue le role d'employee
            default:
                existingEmploye.setRole(Role.EMPLOYEE);
                break;
        }

            return this.employeRepository.save(existingEmploye);
        } else {
            return null;
        }
    }

    /**
     * supprime un employee grace a son id
     * @param id
     */
    public void deleteEmploye(int id) {
        employeRepository.deleteById(id);
    }

    /**
     * recup employé avec mail verifie qu'il existe
     * @param mail
     * @return
     */
    public boolean existEmployeByEmail(String mail) {
        Optional<Employe> employe = employeRepository.findByMail(mail);
        return employe.isPresent();
    }

    public Employe getEmployeByMail(String mail) {
       Employe employee = this.employeRepository.getEmployeByMail(mail);
        return employee;
    }

}
