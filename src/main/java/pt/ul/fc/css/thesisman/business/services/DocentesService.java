package pt.ul.fc.css.thesisman.business.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.ul.fc.css.thesisman.business.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.business.dtos.DocenteDTO;
import pt.ul.fc.css.thesisman.business.dtos.PropostaTeseDTO;
import pt.ul.fc.css.thesisman.business.dtos.SalaDTO;
import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.DateNotAllowedException;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.handlers.DocenteHandler;

@Component
public class DocentesService {

	@Autowired
	private DocenteHandler docenteHandler;

	public List<String> getMestrados() {
		return docenteHandler.getMestrados();
	}

	public void submeterTemaDocente(Long idDocenteCorrente, String titulo, String descricao, List<String> mestrados,
			float renumeracao, Long idAnoLectivo) throws EmptyFieldsException, BadValueException, InvalidIdException {

		docenteHandler.submeterTemaDocente(idDocenteCorrente, titulo, descricao, mestrados, renumeracao, idAnoLectivo);	
	}

	public void registarNotaDefesa(Long idOrientador, Long idDefesa, int nota) throws DuplicateActionException, BadValueException, InvalidIdException {
		docenteHandler.registarNotaDefesa(idOrientador, idDefesa, nota);		
	}

	public void registarNotaDefesaFinal(Long idOrientador, Long idDefesa, int nota) throws DuplicateActionException, BadValueException, InvalidIdException {
		docenteHandler.registarNotaDefesa(idOrientador, idDefesa, nota);

	}

	public void loginUniversidade(String username, String password) throws EmptyFieldsException, LoginErrorException {
		docenteHandler.loginDocente(username, password);

	}

	public DocenteDTO getDocente(String username) {
		return docenteHandler.getDocente(username);
	}

	public List<TemaDTO> getTemasSubmetidos(Long idDocenteCorrente) throws InvalidIdException {
		return docenteHandler.getTemasSubmetidos(idDocenteCorrente);
	}
	
	public void marcarDefesaPresencial(Long docenteID, Long arguenteId, Long alunoId, Long salaId, LocalDateTime data) throws InvalidIdException, EmptyFieldsException {
		docenteHandler.addDefesaPresencial(docenteID, arguenteId, alunoId, salaId, data);
	}

	public List<PropostaTeseDTO> getPropostasTese(Long docenteID) throws InvalidIdException {
		return docenteHandler.getPropostaTese(docenteID);
	}

	public List<DocenteDTO> getDocentesComExcecao(Long idDocenteCorrente, LocalDateTime data, int duracao) {
		return docenteHandler.getDocentesComExcecao(idDocenteCorrente, data, duracao);
	}

	public List<SalaDTO> getSalasDisponiveis(LocalDateTime data, int duracao) {
		return docenteHandler.getSalasDisponiveis(data, duracao);
	}

	public List<LocalDateTime> getAgenda(Long idDocenteCorrente) throws InvalidIdException {
		return docenteHandler.getAgenda(idDocenteCorrente);
	}

	public List<AlunoDTO> getAlunosAAvaliar(Long idDocenteCorrente) throws InvalidIdException {
		return docenteHandler.getAlunosAOrientar(idDocenteCorrente);
	}

	public long getDocenteId(String username) {
		return docenteHandler.getDocenteId(username);
	}

	public void atribuirTema(long alunoId, long temaId) {
		docenteHandler.atribuirTemaAAluno(alunoId, temaId);
		
	}

	public List<AlunoDTO> getAlunos() {
		return docenteHandler.getAlunos();
	}



}
