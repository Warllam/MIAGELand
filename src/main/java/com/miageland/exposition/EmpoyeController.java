package com.miageland.exposition;

import com.miageland.DTO.EmployeDTO;
import com.miageland.metier.EmployeService;
import com.miageland.model.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmpoyeController {
    @Autowired
    private EmployeService employeService;

    /**
     * Endpoint dajout employé
     * @param employe
     * @return
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Employe> createEmploye(@RequestBody EmployeDTO employe) {
        Employe e = employeService.newEmploye(employe);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }



    /**
     * Endpoint suppression employé
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint update employe
     * @param id
     * @param employe
     * @return l'employe mis a jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmployee(@PathVariable int id, @RequestBody EmployeDTO employe) {
        return ResponseEntity.ok(employeService.updateEmploye(id, employe));
    }

    /**
     * recup liste employee
     * @return liste employee
     */
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployees() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    /**
     * recup employee avec son id
     * @param id
     * @return employee d'id id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeService.getEmployeById(id));
    }

    /**
     * recup nom employee avec id
     * @param id
     * @return nom employee
     */
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getEmployeeNameById(@PathVariable int id) {
        // Récup employé
        Employe employe = employeService.getEmployeById(id);

        // existe ?
        if (employe == null) {
            //"Employé non trouvé"
            return null;
        }

        return ResponseEntity.ok(employe.getNom());
    }

    /**
     * recup role employee
     * @param id
     * @param role
     * @return l'employee modifié
     */
    /*@PutMapping("/employee/{id}/role")
    public ResponseEntity<Employe> updateEmployeeRole(@PathVariable int id, @PathVariable Role role) {
        // Recup employé
        Employe employee = employeeService.getEmployeeById(id);

        // existe ?
        if (employee == null) {
            return null;
        }

        // Modifier le rôle de l'employé
        employee.setRole(role);

        // Maj employé
        Employe updatedEmployee = employeeService.updateEmployee(id ,employee);
        return ResponseEntity.ok(updatedEmployee);
    }
*/
    /**
     * recup role
     * @param id
     * @return role
     */
   /* @GetMapping("/employee/{id}/role")
    public ResponseEntity<Role> getEmployeeRole(@PathVariable int id) {
        // Recup employé p
        Employe employee = employeeService.getEmployeeById(id);

        // existe ?
        if (employee == null) {
            return null;
        }
        // Recup role
        Role role = employee.getRole();
       return ResponseEntity.ok(role);
    }
*/
}
