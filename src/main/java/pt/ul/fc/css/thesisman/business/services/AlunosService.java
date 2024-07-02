package pt.ul.fc.css.thesisman.business.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidOptionException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.exceptions.ThemeLimitException;
import pt.ul.fc.css.thesisman.business.handlers.AlunoHandler;
import pt.ul.fc.css.thesisman.business.handlers.DocenteHandler;

@Component
public class AlunosService {

    @Autowired
    private AlunoHandler alunoHandler;
    
    @Autowired
    private DocenteHandler docenteHandler;
    
    /**
     * Devolve a lista de temas disponiveis no ano letivo selecionado
     * @param idAnoLectivo
     * @return
     */
	public Set<Tema> listaTemasAnoLectivo(Long idAnoLectivo) {
		return docenteHandler.listaTemasAnoLectivo(idAnoLectivo);
	}
	
	/**
	 * devolve a lista de Temas disponiveis para o mestrado do aluno
	 * @param idAluno
	 * @return
	 * @throws NonExistentObjectException 
	 */
	public List<TemaDTO> getTemasDispComMestradoDeAluno(Long idAluno) throws NonExistentObjectException {
		return alunoHandler.getTemasDispComMestradoDeAluno(idAluno);
	}
	
	/**
	 * adiciona o aluno com idAluno a lista de candidatos do tema com idTema
	 * e associa o tema ao numero da opcao escolhida pelo aluno
	 * @param idAluno
	 * @param idTema
	 * @throws ThemeLimitException
	 * @throws DuplicateActionException
	 * @throws InvalidOptionException 
	 */
	public void candidatarATema(Long idAluno, Long idTema) throws ThemeLimitException, DuplicateActionException, InvalidOptionException {
		alunoHandler.candidatarATema(idAluno, idTema);
		
	}
	
	/**
	 * Devolve a lista de Temas escolhidos
	 * @param idAluno
	 * @return
	 */
	public List<TemaDTO> getListaTemasEscolhidos(Long idAluno) {
		return alunoHandler.getListaTemasEscolhidos(idAluno);
	}
	
	/**
	 * Cancela a candidatura ao tema com temaID
	 * @param idAluno
	 * @param temaID
	 * @throws InvalidIdException 
	 */
	public void canCandidatura(Long idAluno, Long temaID) throws InvalidIdException {
		alunoHandler.canCandidatura(idAluno, temaID);
		
	}
	
	/**
	 * Realiza o login do aluno
	 * @param username
	 * @param password
	 * @throws EmptyFieldsException
	 * @throws LoginErrorException
	 */
	public void loginUniversidade(String username, String password) throws EmptyFieldsException, LoginErrorException {
		alunoHandler.loginAluno(username, password);
	}

	public long getAlunoId(String nAluno) {
		return this.alunoHandler.getAlunoId(nAluno);
	}	
	
}
