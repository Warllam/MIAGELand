package com.miageland;

import com.miageland.DTO.EmployeDTO;
import com.miageland.metier.EmployeService;
import com.miageland.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.miageland.exposition.EmployeController;


@SpringBootApplication
public class MiagelandApplication implements CommandLineRunner {

	@Autowired
	private EmployeService employeService;

	public static void main(String[] args) {
		SpringApplication.run(MiagelandApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.employeService.newEmploye("Lamaud", "Paul", "paul@lamaud.fr", Role.GERANT);
	}
}
