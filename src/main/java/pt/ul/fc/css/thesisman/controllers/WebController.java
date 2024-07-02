package pt.ul.fc.css.thesisman.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidOptionException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.exceptions.ThemeLimitException;
import pt.ul.fc.css.thesisman.business.services.AlunosService;
import pt.ul.fc.css.thesisman.business.services.DocentesService;
import pt.ul.fc.css.thesisman.business.services.UtilEmpService;

@Controller
public class WebController {

	@Autowired
	private AlunosService alunoService;

	@Autowired
	private DocentesService docenteService;

	@Autowired
	private UtilEmpService utilizadorEmpService;

	@Autowired
	private UtilEmpService utilEmpService;

	public WebController() {
		super();
	}

	@GetMapping({"/", "login"})
	public String login(final Model model) {
		return "login";
	}

	@PostMapping("loginDocente")
	public String loginDocente(final Model model, 
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws EmptyFieldsException {
		try {
			model.addAttribute("idDocenteCorrente", docenteService.getDocenteId(username));
			docenteService.loginUniversidade(username, password);
		}catch(EmptyFieldsException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}catch(LoginErrorException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}
		return "docente_page";
	}

	@PostMapping("loginAluno")
	public String loginAluno(final Model model, 
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws EmptyFieldsException {
		try {
			alunoService.loginUniversidade(username, password);
			model.addAttribute("alunoId", alunoService.getAlunoId(username));
		}catch(EmptyFieldsException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}catch(LoginErrorException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}
		return "aluno_page";
	}

	@PostMapping("loginEmp")
	public String loginUtilEmp(final Model model, 
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws EmptyFieldsException {
		try {
			model.addAttribute("idUtilEmpCorrente", utilizadorEmpService.getUtilizador(email));
			utilizadorEmpService.loginUtilizadorEmp(email, password);
		}catch(EmptyFieldsException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}catch(LoginErrorException e) {
			model.addAttribute(e.getMessage());
			return "redirect:/login";
		}
		return "utilEmp_page";
	}

	@GetMapping("/register")
	public String createUtilEmp() {
		return "create_utilEmp";
	}

	@PostMapping("/register")
	public String createUtilEmp(final Model model,
			@RequestParam("nome") String nome,
			@RequestParam("apelido") String apelido,
			@RequestParam("empresa") String empresa,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		try {
			utilizadorEmpService.registarUtilEmp(nome, apelido, empresa, email, password);
		} catch (EmptyFieldsException e) {
			model.addAttribute("error", e.getMessage());
			return "create_utilEmp";
		}
		model.addAttribute("Conta criada com sucesso");
		return "redirect:/login";
	}

	@PostMapping("/temas")
	public String getTemas(final Model model, @RequestParam("idAluno") Long idAluno) {
		try {
			model.addAttribute("alunoId", idAluno);
			model.addAttribute("temas", alunoService.getTemasDispComMestradoDeAluno(idAluno));
		}catch (NonExistentObjectException e) {
			model.addAttribute("error", e.getMessage());
			return "aluno_page";
		}
		return "temas_list";
	}

	@PostMapping("/candidatar")
	public String candidatarATema(final Model model,
			@RequestParam("idAluno") Long idAluno,
			@RequestParam("idTema") Long idTema) {
		try {
			alunoService.candidatarATema(idAluno, idTema);
			model.addAttribute("alunoId", idAluno);
			model.addAttribute("temas", alunoService.getTemasDispComMestradoDeAluno(idAluno));
		} catch (ThemeLimitException | DuplicateActionException | InvalidOptionException | NonExistentObjectException e) {
			model.addAttribute("error", e.getMessage());
			return "aluno_page";
		} 
		return "temas_list";
	}

	@PostMapping("/temasEscolhidos")
	public String getTemasEscolhidos(final Model model, @RequestParam("idAluno") Long idAluno) {
		model.addAttribute("alunoId", idAluno);
		model.addAttribute("temas", alunoService.getListaTemasEscolhidos(idAluno));
		return "temas_escolhidos_list";
	}

	@PostMapping("/CancelarCandidatura")
	public String CancelarCandidatura(final Model model,
			@RequestParam("idAluno") Long idAluno,
			@RequestParam("idTema") Long idTema){
		try {
			alunoService.canCandidatura(idAluno, idTema);
			model.addAttribute("alunoId", idAluno);
			model.addAttribute("temas", alunoService.getListaTemasEscolhidos(idAluno));
		} catch (InvalidIdException e) {
			model.addAttribute("error", e.getMessage());
			return "aluno_page";
		} 
		return "temas_escolhidos_list";
	}
	//Endpoint to registar nota final da tese 
	@PostMapping("/registarNotaDefesa")
    public String registarNotaDefesa(final Model model,
                                     @RequestParam("idPresidente") Long idPresidente,
                                     @RequestParam("idDefesaFinal") Long idDefesaFinal,
                                     @RequestParam("nota") int nota) {
        try {
            docenteService.registarNotaDefesa(idPresidente, idDefesaFinal, nota);
            model.addAttribute("mensagemSucesso", "Nota registrada com sucesso.");
        } catch (DuplicateActionException | BadValueException | InvalidIdException e) {
            model.addAttribute("error", e.getMessage());
            return "docente_page";  // ou uma página de erro apropriada
        }
        return "redirect:/docente_page";  // ou a página que você deseja redirecionar após o sucesso
    }


	//Endpoint to create a theme as a utilizador empresarial
	@GetMapping("/util-emp/create_tema")
	public String createTemaUtilEmp(Model model, @RequestParam("idUtilEmpCorrente") Long idUtilEmpCorrente) {
		model.addAttribute("idUtilEmpCorrente", idUtilEmpCorrente);
		model.addAttribute("docentes", utilEmpService.getDelegados());
		model.addAttribute("mestrados", utilEmpService.getMestrados());
		model.addAttribute("anosLetivos", utilEmpService.getAnosLetivos());
		return "submeter_temas_util_empresarial";
	}

	// Endpoint to submit a new Tema as a utilizador empresarial
	@PostMapping("/util-emp/submeter")
	public String submeterTemaUtilEmp(Model model,
			@RequestParam("idUtilEmpCorrente") Long idUtilEmpCorrente,
			@RequestParam ("titulo") String titulo,
			@RequestParam ("descricao") String descricao,
			@RequestParam ("mestradosEscolhidos") List<String> mestradosEscolhidos,
			@RequestParam ("remuneracao") float remuneracao,
			@RequestParam ("idAnoLectivo") Long idAnoLectivo,
			@RequestParam ("idOrientador") Long idOrientador) {
		try {
			utilEmpService.submeterTema(idUtilEmpCorrente, titulo, descricao, mestradosEscolhidos, remuneracao, idAnoLectivo, idOrientador);
			model.addAttribute("idUtilEmpCorrente", idUtilEmpCorrente);
			model.addAttribute("temas", utilEmpService.getTemasSubmetidos(idUtilEmpCorrente));
		} catch (EmptyFieldsException | BadValueException | InvalidIdException e) {
			createTemaUtilEmp(model, idUtilEmpCorrente);
		} 
		return "temas_submetidos_util_emp";
	}

	//Endpoint to create a theme
	@GetMapping("/docente/create_tema")
	public String createTemaDocente(Model model, @RequestParam("idDocenteCorrente") Long idDocenteCorrente) {
		model.addAttribute("idDocenteCorrente", idDocenteCorrente);
		model.addAttribute("mestrados", docenteService.getMestrados());
		model.addAttribute("anosLetivos", utilEmpService.getAnosLetivos());
		return "submeter_temas_docente";
	}

	// Endpoint to submit a new Tema
	@PostMapping("/docente/submeter")
	public String submeterTemaDocente(Model model,
			@RequestParam("idDocenteCorrente") Long idDocenteCorrente,
			@RequestParam ("titulo") String titulo,
			@RequestParam ("descricao") String descricao,
			@RequestParam ("mestradosEscolhidos") List<String> mestradosEscolhidos,
			@RequestParam ("remuneracao") float remuneracao,
			@RequestParam ("idAnoLectivo") Long idAnoLectivo) {
		try {
			docenteService.submeterTemaDocente(idDocenteCorrente, titulo, descricao, mestradosEscolhidos, remuneracao, idAnoLectivo);
			model.addAttribute("idDocenteCorrente", idDocenteCorrente);
			model.addAttribute("temas", docenteService.getTemasSubmetidos(idDocenteCorrente));
		} catch (EmptyFieldsException | BadValueException | InvalidIdException e) {
			createTemaDocente(model, idDocenteCorrente);
		} 
		return "temas_submetidos_docente";
	}

	//Endpoint para marcar a defesa
	@GetMapping("/docente/marcar_defesa")
	public String marcarDefesaProposta(Model model, @RequestParam("idDocenteCorrente") Long idDocenteCorrente) {
		try {
			docenteService.atribuirTema((long)2, (long)2);
			model.addAttribute("idDocenteCorrente", idDocenteCorrente);
			model.addAttribute("alunos", docenteService.getAlunosAAvaliar(idDocenteCorrente));
			model.addAttribute("agenda", docenteService.getAgenda(idDocenteCorrente));
		} catch (InvalidIdException e) {
			model.addAttribute("error", e.getMessage());
			return "docente_page";
		}
		return "create_defesa";
	}

	//Endpoint to create a defesa
	@GetMapping("/docente/create_defesa")
	public String createDefesaProposta(Model model, 
			@RequestParam("idDocenteCorrente") Long idDocenteCorrente,
			@RequestParam("idAluno") Long idAluno,
			@RequestParam("data") LocalDateTime data) {

		model.addAttribute("idDocenteCorrente", idDocenteCorrente);
		model.addAttribute("idAluno", idAluno);
		model.addAttribute("data", data);
		model.addAttribute("docentes", docenteService.getDocentesComExcecao(idDocenteCorrente, data, 60));
		model.addAttribute("salas", docenteService.getSalasDisponiveis(data, 60));
		return "create_defesa_2";
	}

	//Endpoint para agendar a defesa
	@PostMapping("/docente/agendar_defesa_presencial")
	public String AgendarDefesaPropostaPresencial(Model model, 
			@RequestParam("idDocenteCorrente") Long idDocenteCorrente,
			@RequestParam("idAluno") Long idAluno,
			@RequestParam("data") LocalDateTime data,
			@RequestParam("idArguente") Long idArguente,
			@RequestParam("idSala") Long idSala) {
		try {

			docenteService.marcarDefesaPresencial(idDocenteCorrente, idArguente, idAluno, idSala, data);
			model.addAttribute("idDocenteCorrente", idDocenteCorrente);
		} catch (InvalidIdException | EmptyFieldsException e) {
			model.addAttribute("error", e.getMessage());

		}
		return "docente_page";
	}

	//Endpoint para escolher o tema e o aluno a serem associados
	@GetMapping("/docente/temas_e_alunos")
	public String gettemasEAlunos(Model model, @RequestParam("idDocenteCorrente") Long idDocenteCorrente) {
		try {
			model.addAttribute("idDocenteCorrente", idDocenteCorrente);
			model.addAttribute("alunos", docenteService.getAlunos());
			model.addAttribute("temas", docenteService.getTemasSubmetidos(idDocenteCorrente));
		} catch (InvalidIdException e) {
			model.addAttribute("error", e.getMessage());
			return "docente_page";
		}
		return "tema_e_alunos";
	}

	//Endpoint para escolher o tema e o aluno a serem associados
	@PostMapping("/docente/atribuir_tema")
	public String atribuirTema(Model model, @RequestParam("idDocenteCorrente") Long idDocenteCorrente, 
			@RequestParam("idAluno") Long idAluno,
			@RequestParam("idTema") Long idTema) {
		docenteService.atribuirTema(idAluno, idTema);
		model.addAttribute("idDocenteCorrente", idDocenteCorrente);
		return "tema_e_alunos";
	}

}
