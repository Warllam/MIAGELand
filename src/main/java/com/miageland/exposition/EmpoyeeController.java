package com.miageland.exposition;

import com.miageland.metier.EmployeeService;
import com.miageland.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmpoyeeController {
    @Autowired
    private EmployeeService employeeService;


}
