package pt.ul.fc.css.thesisman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.services.UtilEmpService;

@RestController()
@RequestMapping("/api/util-emp")
public class UtilizadorEmpController {
	
	@Autowired
	UtilEmpService utilEmpService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUtilEmpresarial(@RequestParam String nome, @RequestParam String apelido,
			@RequestParam String empresa, @RequestParam String email, @RequestParam String password) {
		try {
			utilEmpService.registarUtilEmp(nome, apelido, empresa, email, password);
			return ResponseEntity.ok("UtilEmpresarial registered successfully");
		} catch (EmptyFieldsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	@PostMapping("/empresarial")
	public ResponseEntity<String> loginEmpresarial(@RequestParam String username, @RequestParam String password) {
		try {
			utilEmpService.loginUtilizadorEmp(username, password);
			return ResponseEntity.ok("Login successful");
		} catch (EmptyFieldsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (LoginErrorException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}
	
	
    
    
    
}
