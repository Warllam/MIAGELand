package com.miageland.exposition;

import com.miageland.metier.EmployeeService;
import com.miageland.model.Employee;
import com.miageland.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmpoyeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint dajout employé
     * @param employee
     * @return
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    /**
     * Endpoint suppression employé
     * @param id
     * @return
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint update employee
     * @param id
     * @param employee
     * @return l'employee mis a jour
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    /**
     * recup liste employee
     * @return liste employee
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * recup employee avec son id
     * @param id
     * @return employee d'id id
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * recup nom employee avec id
     * @param id
     * @return nom employee
     */
    @GetMapping("/employee/{id}/name")
    public ResponseEntity<String> getEmployeeNameById(@PathVariable int id) {
        // Récup employé
        Employee employee = employeeService.getEmployeeById(id);

        // existe ?
        if (employee == null) {
            //"Employé non trouvé"
            return null;
        }

        return ResponseEntity.ok(employee.getNom());
    }

    /**
     * recup role employee
     * @param id
     * @param role
     * @return l'employee modifié
     */
    @PutMapping("/employee/{id}/role")
    public ResponseEntity<Employee> updateEmployeeRole(@PathVariable int id, @PathVariable Role role) {
        // Recup employé
        Employee employee = employeeService.getEmployeeById(id);

        // existe ?
        if (employee == null) {
            return null;
        }

        // Modifier le rôle de l'employé
        employee.setRole(role);

        // Maj employé
        Employee updatedEmployee = employeeService.updateEmployee(id ,employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * recup role
     * @param id
     * @return role
     */
    @GetMapping("/employee/{id}/role")
    public ResponseEntity<Role> getEmployeeRole(@PathVariable int id) {
        // Recup employé p
        Employee employee = employeeService.getEmployeeById(id);

        // existe ?
        if (employee == null) {
            return null;
        }
        // Recup role
        Role role = employee.getRole();
       return ResponseEntity.ok(role);
    }

}
