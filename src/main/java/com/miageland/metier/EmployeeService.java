package com.miageland.metier;

import com.miageland.DAO.EmployeeRepository;
import com.miageland.DTO.AttractionDTO;
import com.miageland.DTO.EmployeeDTO;
import com.miageland.model.Attraction;
import com.miageland.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private  EmployeeRepository employeeRepository;
    public EmployeeService() {

    }
    /*private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }*/

    //liste des employees
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    /**
     * employee en fonction de leur id
     * @param id
     * @return l'employe
     */
    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RuntimeException("Employee non trouvé avec l'id " + id);
        }
    }

    /**
     * création d'un employee
     * @param newEmployee
     * @return l'employee créé et sauvegardé
     */
    public Employee newEmployee(EmployeeDTO newEmployee) {
        Employee e =new Employee(newEmployee.getNom(),newEmployee.getPrenom(), newEmployee.getMail(),newEmployee.getRole());
        this.employeeRepository.save(e);
        return e;
        //return "Création ok";
    }


    /**
     * modif informations d'un employee grace a son id
     * @param id
     * @param updatedEmployee
     * @return l'employee modifié
     */
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setPrenom(updatedEmployee.getPrenom());
            existingEmployee.setNom(updatedEmployee.getNom());
            existingEmployee.setMail(updatedEmployee.getMail());
            existingEmployee.setRole(updatedEmployee.getRole());

            return this.employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }

    /**
     * supprime un employee grace a son id
     * @param id
     */
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    /**
     * recip employé avec mail
     * @param email
     * @return
     */
    public boolean existEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByMail(email);
        return employee.isPresent();
    }

}
