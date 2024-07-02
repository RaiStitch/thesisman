package pt.ul.fc.css.thesisman.controllers;



import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.exceptions.ThemeLimitException;
import pt.ul.fc.css.thesisman.business.services.AlunosService;


@RestController()
@RequestMapping("api/alunos")
public class AlunoController {

    @Autowired
    private AlunosService alunosService;

    // Listar temas de um ano letivo
    @GetMapping("/ano-lectivo/{idAnoLectivo}")
    public ResponseEntity<?> listarTemas(@PathVariable Long idAnoLectivo) throws InvalidIdException {
        try {
            Set<Tema> temas = alunosService.listaTemasAnoLectivo(idAnoLectivo);
            return ResponseEntity.ok(temas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Listar temas disponÃ­veis para um aluno
    @GetMapping("/disponiveis/{idAluno}")
    public ResponseEntity<?> getTemasDisponiveis(@PathVariable long idAluno) {
        List<TemaDTO> temasDisponiveis;
		try {
			temasDisponiveis = alunosService.getTemasDispComMestradoDeAluno(idAluno);
		} catch (NonExistentObjectException e) {
			return ResponseEntity.ok(e.getMessage());
		}
        return ResponseEntity.ok(temasDisponiveis);
    }

    // Candidatar-se a um tema
    @PostMapping("/candidatar")
    public ResponseEntity<String> candidatarATema(@RequestParam Long idAluno, @RequestParam Long idTema, @RequestParam int opcao) {
        try {
            alunosService.candidatarATema(idAluno, idTema);
            return ResponseEntity.ok("Candidatura ao tema realizada com sucesso");
        } catch (ThemeLimitException | DuplicateActionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
        }
    }

    // Listar temas escolhidos por um aluno
    @GetMapping("/temas-escolhidos/{nAluno}")
    public ResponseEntity<List<TemaDTO>> getTemasEscolhidos(@PathVariable Long idAluno) throws InvalidIdException {
        List<TemaDTO> temasEscolhidos = alunosService.getListaTemasEscolhidos(idAluno);
		return ResponseEntity.ok(temasEscolhidos);
    }

    // Cancelar candidatura a um tema
    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelarCandidatura(@RequestParam Long idAluno, @RequestParam Long temaID) throws InvalidIdException {
        try {
            alunosService.canCandidatura(idAluno, temaID);
            return ResponseEntity.ok("Candidatura cancelada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            alunosService.loginUniversidade(username, password);
            return ResponseEntity.ok("Login successful");
        } catch (EmptyFieldsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (LoginErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    
    
    @PostMapping("/loginAluno")
    boolean userExists(@RequestBody String json) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        JsonNode jsonNode = objectMapper.readTree(json);
        String username = jsonNode.get("username").asText();
        alunosService.loginUniversidade(username, "mock");
        return true;
      }catch (Exception e) {
        return false;
      }
    }
}
