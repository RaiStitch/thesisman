package pt.ul.fc.css.thesisman.business.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.ul.fc.css.thesisman.business.dtos.AnoLetivoDTO;
import pt.ul.fc.css.thesisman.business.dtos.DocenteDTO;
import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.dtos.UtilEmpresarialDTO;
import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.UnavaliableNameException;
import pt.ul.fc.css.thesisman.business.handlers.DocenteHandler;
import pt.ul.fc.css.thesisman.business.handlers.UtilEmpHandler;



@Component
public class UtilEmpService {
	@Autowired
	private UtilEmpHandler utilEmpHandler;
	
	@Autowired
	private DocenteHandler docenteHandler;
	
	/**
	 * Regista um novo utilizador empresarial
	 * @param nome
	 * @param pwd
	 * @param apelido
	 * @param empresa
	 * @throws EmptyFieldsException
	 * @throws UnavaliableNameException
	 */
	public void registarUtilEmp(String nome, String apelido, String empresa, String email, String password) throws EmptyFieldsException {
		utilEmpHandler.add(nome, apelido, empresa, email, password);
	}
	
	/**
	 * Cria um tema
	 * @param utilEmp
	 * @param titulo
	 * @param descricao
	 * @param mestrados
	 * @param remuneracao
	 * @throws EmptyFieldsException
	 * @throws InvalidIdException 
	 * @throws BadValueException 
	 */
	public void submeterTema(Long utilEmp, String titulo, String descricao, List<String> mestrados, float remuneracao, Long anoLetivoId, Long orientadorId) throws EmptyFieldsException, BadValueException, InvalidIdException {
		utilEmpHandler.submeterTemaUtilEmp(utilEmp, titulo, descricao, mestrados, remuneracao, anoLetivoId, orientadorId);
	}
	
	/**
	 * login do utilizador empresarial
	 * @param username
	 * @param password
	 * @throws LoginErrorException
	 * @throws EmptyFieldsException
	 */
	public void loginUtilizadorEmp(String username, String password) throws LoginErrorException, EmptyFieldsException {
		
		utilEmpHandler.loginUtilEmpresarial(username, password);
	}
	
	/**
	 * Devolve a lista de mestrados 
	 * @return
	 */
	public List<String> getMestrados() {
		return docenteHandler.getMestrados();
	}

	public UtilEmpresarialDTO getUtilizador(String email) {
		return utilEmpHandler.getUtilizadorEmp(email);
	}

	public List<DocenteDTO> getDelegados() {
		return docenteHandler.getDocentes();
	}

	public List<AnoLetivoDTO> getAnosLetivos() {
		return utilEmpHandler.getAnoLetivos();
	}

	public List<TemaDTO> getTemasSubmetidos(Long idUtilEmpCorrente) {
		return utilEmpHandler.getTemasSubmetidos(idUtilEmpCorrente);
	}
	
	
}
