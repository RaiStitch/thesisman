package pt.ul.fc.css.thesisman.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.DateNotAllowedException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.services.DocentesService;


@RestController()
@RequestMapping("/api/docentes")
public class DocenteController {
	
	@Autowired
	private DocentesService docentesService;

    // Endpoint to get the list of Mestrados
    @GetMapping("/mestrados")
    public ResponseEntity<List<String>> getMestrados() {
        List<String> mestrados = docentesService.getMestrados();
        return ResponseEntity.ok(mestrados);
    }

    // Endpoint to submit a new Tema
    @PostMapping("/submeter")
    public ResponseEntity<String> submeterTema(
            @RequestParam Long idDocenteCorrente,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam List<String> mestrados,
            @RequestParam float renumeracao,
            @RequestParam Long idAnoLectivo) {
        try {
            docentesService.submeterTemaDocente(idDocenteCorrente, titulo, descricao, mestrados, renumeracao, idAnoLectivo);
            return ResponseEntity.ok("Tema submetido com sucesso");
        } catch (EmptyFieldsException | BadValueException | InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Endpoint to register a nota defesa
    @PostMapping("/registrarNota")
    public ResponseEntity<String> registarNotaDefesa(
            @RequestParam Long idOrientador,
            @RequestParam Long idDefesa,
            @RequestParam int nota) {
        try {
            docentesService.registarNotaDefesa(idOrientador, idDefesa, nota);
            return ResponseEntity.ok("Nota atribuÃ­da com sucesso");
        } catch (InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar a nota da defesa");
        }
    }
    
 // Endpoint to register a nota defesa
    @PostMapping("/registrarNotaFinal")
    public ResponseEntity<String> registrarNotaDefesa(
            @RequestParam Long idOrientador,
            @RequestParam Long idDefesa,
            @RequestParam int nota) {
        try {
            docentesService.registarNotaDefesaFinal(idOrientador, idDefesa, nota);
            return ResponseEntity.ok("Nota atribuÃ­da com sucesso");
        } catch (InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar a nota da defesa");
        }
    }

    // Endpoint for login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            docentesService.loginUniversidade(username, password);
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

