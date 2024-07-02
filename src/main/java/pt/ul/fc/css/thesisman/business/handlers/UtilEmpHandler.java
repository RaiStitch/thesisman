package pt.ul.fc.css.thesisman.business.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.business.dtos.AnoLetivoDTO;
import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.dtos.UtilEmpresarialDTO;
import pt.ul.fc.css.thesisman.business.entities.AnoLectivo;
import pt.ul.fc.css.thesisman.business.entities.Docente;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.entities.UtilEmpresarial;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;
import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.repositories.AnoLectivoRepository;
import pt.ul.fc.css.thesisman.business.repositories.DocenteRepository;
import pt.ul.fc.css.thesisman.business.repositories.TemaRepository;
import pt.ul.fc.css.thesisman.business.repositories.UtilEmpresarialRepository;

@Component
public class UtilEmpHandler {
	@Autowired 
	private UtilEmpresarialRepository utilEmpresarialRepository;
	
	@Autowired
	private AnoLectivoRepository anoLectivoRepository;
	
	@Autowired
	private DocenteRepository docenteRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	public void loginUtilEmpresarial(String email, String password) throws LoginErrorException, EmptyFieldsException {
		if(email.isEmpty()) {
			throw new EmptyFieldsException("Email is a required field");
		}
		if(password.isEmpty()) {
			throw new EmptyFieldsException("Password is a required field");
		}
		
		Optional<UtilEmpresarial> utilEmpresarial = utilEmpresarialRepository.findByEmail(email);
		
		if(utilEmpresarial.isPresent()) {
			if(!utilEmpresarial.get().getPassword().equals(password)) {
				throw new LoginErrorException("Password incorreta");
			}
		}else {
			throw new LoginErrorException("Email incorreto");
		}
			
	}
	
	@Transactional(readOnly = true)
	public UtilEmpresarialDTO getUtilizadorEmp(String email) {
		return dtofyUtilEmp(utilEmpresarialRepository.findByEmail(email).get());
	}
	
	@Transactional
	public void add (String nome, String apelido, String empresa, String email, String password) throws EmptyFieldsException {
		if (nome == null ){
			throw new EmptyFieldsException("nome is a required field");
		}
		
		if (apelido == null) {
			throw new EmptyFieldsException("apelido is a required field");
		} 
		
		if (empresa == null) {
			throw new EmptyFieldsException("empresa is a required field");
		} 
		
		if (email == null) {
			throw new EmptyFieldsException("email is a required field");
		} 
		
		if (password == null) {
			throw new EmptyFieldsException("password is a required field");
		} 
		
		UtilEmpresarial utilEmp = new UtilEmpresarial(nome, apelido, empresa, email, password);
		utilEmpresarialRepository.save(utilEmp);
	}
	
	@Transactional(readOnly = true)
	public List<TemaDTO> getTemasSubmetidos(Long idUtilEmpCorrente) {
	    return utilEmpresarialRepository.findById(idUtilEmpCorrente).get().getTemasSubmetidos().stream().map(UtilEmpHandler::dtfyTemas).toList();
	}
	
	/**
	 * Submete um tema atraves de um utilizador empresarial
	 * @param utilEmp_id
	 * @param titulo
	 * @param descricao
	 * @param possiveis_mestrados
	 * @param remuneracao
	 * @param anoLetivoId
	 * @param orinetador_id
	 * @throws EmptyFieldsException
	 * @throws BadValueException
	 * @throws InvalidIdException
	 */
	@Transactional
	public void submeterTemaUtilEmp(Long utilEmp_id, String titulo, String descricao, List<String> possiveis_mestrados, float remuneracao, Long anoLetivoId, Long orinetador_id) throws EmptyFieldsException, BadValueException, InvalidIdException {
		
		if (titulo .isEmpty()) {
			throw new EmptyFieldsException("titulo is a required field");
		}
		
		if (descricao.isEmpty()) {
			throw new EmptyFieldsException("descricao is a required field");
		}
		
		if (possiveis_mestrados.isEmpty()){
			throw new EmptyFieldsException("mestrados is a required field");
		}
	
		if(remuneracao <= 0) {
			throw new BadValueException("renumeracao must be positive");
		}
		
		if(titulo.length() > 130) {
			throw new BadValueException("title has a limit of 130 characters");
		}
		
		
		//procura por um ano letivo atraves do seu id
		Optional<AnoLectivo> possivel_al = anoLectivoRepository.findById(anoLetivoId);
		if (!possivel_al.isPresent()) {
			throw new InvalidIdException("There is no utilizador Empresarial with this id"); 
		}
		
		//procura por um docente atravez do seu id
		Optional<Docente> possivel_orientador_interno = docenteRepository.findById(orinetador_id);
		if (!possivel_orientador_interno.isPresent()) {
			throw new InvalidIdException("There is no utilizador Empresarial with this id"); 
		}
		
		
		//converte a lista de string para uma lista de mestrados
		List<Mestrados> mestrados = possiveis_mestrados.stream().map(Mestrados::valueOf).toList();
		
		AnoLectivo al = possivel_al.get();
		Docente orientador_interno = possivel_orientador_interno.get();
		
		//cria um novo tema
		Tema tema = new Tema(titulo, descricao, mestrados, remuneracao, al);
		temaRepository.save(tema);
		
		//procura por um utilizador empresarial atraves do seu id
		Optional<UtilEmpresarial> possivel_ue = utilEmpresarialRepository.findById(utilEmp_id);
		if (!possivel_ue.isPresent()) {
			throw new InvalidIdException("There is no utilizador Empresarial with this id");
		}
		
		//guarda o tema na lista de temas propostos
		UtilEmpresarial ue = possivel_ue.get();
		ue.addTema(tema);
		utilEmpresarialRepository.save(ue);
		
		//guarda o tema na lista de temas a orientar
		orientador_interno.addTemaAOrientar(tema);
		docenteRepository.save(orientador_interno);
		
		al.addTema(tema);
		anoLectivoRepository.save(al);
		
		docenteRepository.flush();
		anoLectivoRepository.flush();
		temaRepository.flush();
		utilEmpresarialRepository.flush();
	}
	
	
	@Transactional(readOnly = true)
	public List<AnoLetivoDTO> getAnoLetivos() {
		return anoLectivoRepository.findAll().stream().map(UtilEmpHandler::dtofyAnoLetivo).toList();	
	}
	
	/*
	 * Converts AnoLectivo to AnoLetivoDTO
	 */
	private static AnoLetivoDTO dtofyAnoLetivo(AnoLectivo a) {
		AnoLetivoDTO a2 = new AnoLetivoDTO();
		a2.setId(a.getId());
		a2.setDataInicio(a.getDataInicio());
		a2.setDataFim(a.getDataFim());
		a2.setDescricao(a.getDescricao());
		a2.setAtivo(a.isAtivo());
		return a2;
	}
	
	private static UtilEmpresarialDTO dtofyUtilEmp(UtilEmpresarial a) {
		UtilEmpresarialDTO a2 = new UtilEmpresarialDTO();
		a2.setId(a.getId());
		a2.setNome(a.getNome());
		a2.setApelido(a.getApelido());
		a2.setEmail(a.getEmail());
		a2.setEmpresa(a.getEmpresa());
		a2.setPassword(a.getPassword());
		a2.setTemasSubmetidos(a.getTemasSubmetidos().stream().map(UtilEmpHandler::dtfyTemas).toList());
		return a2;
	}
	
	private static TemaDTO dtfyTemas(Tema temasSubmetidos) {
		TemaDTO tDTO = new TemaDTO();
		tDTO.setId(temasSubmetidos.getId());
		tDTO.setTitulo(temasSubmetidos.getTitulo());
		tDTO.setDescricao(temasSubmetidos.getDescricao());
		tDTO.setMestrados(temasSubmetidos.getMestrados().stream().map(Mestrados::name).toList());
		tDTO.setRemuneracao(temasSubmetidos.getRenumeracao());
		return tDTO;
	}
}
