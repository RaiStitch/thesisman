package pt.ul.fc.css.thesisman.business.handlers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.business.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.business.dtos.DefesaPropostaDTO;
import pt.ul.fc.css.thesisman.business.dtos.DocenteDTO;
import pt.ul.fc.css.thesisman.business.dtos.PropostaTeseDTO;
import pt.ul.fc.css.thesisman.business.dtos.SalaDTO;
import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.entities.Aluno;
import pt.ul.fc.css.thesisman.business.entities.AnoLectivo;
import pt.ul.fc.css.thesisman.business.entities.DefesaProposta;
import pt.ul.fc.css.thesisman.business.entities.Docente;
import pt.ul.fc.css.thesisman.business.entities.PropostaTese;
import pt.ul.fc.css.thesisman.business.entities.Sala;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;
import pt.ul.fc.css.thesisman.business.exceptions.BadValueException;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.repositories.AlunoRepository;
import pt.ul.fc.css.thesisman.business.repositories.AnoLectivoRepository;
import pt.ul.fc.css.thesisman.business.repositories.DefesaPropostaRepository;
import pt.ul.fc.css.thesisman.business.repositories.DocenteRepository;
import pt.ul.fc.css.thesisman.business.repositories.SalaRepository;
import pt.ul.fc.css.thesisman.business.repositories.TemaRepository;

@Component
public class DocenteHandler {
	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private TemaRepository temaRepository;

	@Autowired
	private DocenteRepository docenteRepository;

	@Autowired
	private AnoLectivoRepository anoLectivoRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private DefesaPropostaRepository defesaPropostaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public void loginDocente(String username, String password) throws LoginErrorException, EmptyFieldsException {
		if(username.isEmpty()) {
			throw new EmptyFieldsException("Name is a required field");
		}
		if(password.isEmpty()) {
			throw new EmptyFieldsException("Password is a required field");
		}
		if(docenteRepository.findByNDocente(username) == null) {
			throw new LoginErrorException("The username given does not exist"); 
		}
	}

	public List<DocenteDTO> getDocentes() {
		return docenteRepository.findAll().stream().map(DocenteHandler::dtofyDocente).toList();	
	}

	@Transactional
	public void atribuirTemaAAluno(long alunoId, long temaId) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		Optional<Tema> tema = temaRepository.findById(temaId);
		Docente orientador = docenteRepository.findDocenteByTema(temaId);

		if(aluno.isPresent() && tema.isPresent() && orientador != null) {
			aluno.get().setTema(tema.get());
			aluno.get().setOrientador(orientador);

			tema.get().setAluno(aluno.get());

			orientador.addAlunoAOrientar(aluno.get());

			alunoRepository.save(aluno.get());
			temaRepository.save(tema.get());
			docenteRepository.save(orientador);

			alunoRepository.flush();
			temaRepository.flush();
			docenteRepository.flush();
		}
	}

	@Transactional(readOnly = true)
	public Set<Tema> listaTemasAnoLectivo(Long idAnoLectivo) {
		Optional<AnoLectivo> anoLectivo = anoLectivoRepository.findById(idAnoLectivo);

		if (anoLectivo.isPresent()) {
			return anoLectivo.get().getTemas();
		}
		return null;
	}

	/**
	 * devolve a lista de alunos a orientar
	 * @param docente_id
	 * @return
	 * @throws InvalidIdException
	 */
	@Transactional(readOnly = true)
	public List<AlunoDTO>getAlunosAOrientar(Long docente_id) throws InvalidIdException {
		return alunoRepository.findAlunoByDocente(docente_id).stream().map(DocenteHandler::dtofyAluno).toList();
	}

	/**
	 * devolve a lista com datas em que o docente se encontra ocupado
	 * @param docente_id
	 * @return
	 * @throws InvalidIdException
	 */
	@Transactional(readOnly = true)
	public List<LocalDateTime> getAgenda(Long docente_id) throws InvalidIdException{
		List<LocalDateTime> agenda = new ArrayList<>();
		Optional<Docente> possivelOrientador = docenteRepository.findById(docente_id);
		Docente orientador;
		if (possivelOrientador.isPresent()) {
			orientador = possivelOrientador.get();
		}else {
			throw new InvalidIdException("There is no Docente with this id");
		}

		// Define o intervalo de datas
		LocalDate startDate = LocalDate.of(2024, 1, 10);
		LocalDate endDate = LocalDate.of(2024, 5, 31);

		LocalTime startTime = LocalTime.of(8, 0);
		LocalTime endTime = LocalTime.of(18, 0);

		// Itera sobre cada dia no intervalo
		for (LocalDate currentDate = startDate; currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
			for (LocalTime currentTime = startTime; currentTime.isBefore(endTime.minusMinutes(59)); currentTime = currentTime.plusMinutes(60)) {
				LocalDateTime startDateTime = LocalDateTime.of(currentDate, currentTime);
				LocalDateTime endDateTime = startDateTime.plusMinutes(60);

				// Verifica se o orientador está disponível neste intervalo
				if (orientador.isAvailable(startDateTime, endDateTime)) {
					agenda.add(startDateTime);
				}
			}
		}
		return agenda;
	}

	/**
	 * Verifica se esta disponivel na data data
	 * @param docente_id
	 * @param data
	 * @param duration
	 * @return
	 * @throws InvalidIdException
	 */
	@Transactional(readOnly = true)
	public boolean isAvailable(Long docente_id, LocalDateTime data, int duration) throws InvalidIdException {
		Optional<Docente> possivel_docente = docenteRepository.findById(docente_id);
		if (!possivel_docente.isPresent()) {
			throw new InvalidIdException("There is no Docente with this docente_id");
		}

		Docente docente = possivel_docente.get();
		return docente.isAvailable(data, data.plusMinutes(duration));
	}

	/**
	 * Marca uma defesa de tese final online
	 * @param docente_id
	 * @param arguente_id
	 * @param presidente_id
	 * @param aluno_id
	 * @param link
	 * @param data
	 * @throws InvalidIdException
	 * @throws EmptyFieldsException
	 */
	@Transactional
	public void addDefesaFinalOnline(Long docente_id, Long arguente_id, Long presidente_id,
			Long aluno_id, String link, LocalDateTime data) throws InvalidIdException, EmptyFieldsException {
		addDefesa(docente_id, arguente_id, presidente_id, aluno_id, link, null, data, true, 90);
	}

	/**
	 * Marca uma defesa de tese online
	 * @param docente_id
	 * @param arguente_id
	 * @param presidente_id
	 * @param aluno_id
	 * @param link
	 * @param data
	 * @throws InvalidIdException
	 * @throws EmptyFieldsException
	 */
	@Transactional
	public void addDefesaOnline(Long docente_id, Long arguente_id, Long aluno_id, String link, LocalDateTime data) throws InvalidIdException, EmptyFieldsException {
		addDefesa(docente_id, arguente_id, null, aluno_id, link, null, data, false, 60);
	}

	/**
	 * Marca uma defesa de tese final presencial
	 * @param docente_id
	 * @param arguente_id
	 * @param presidente_id
	 * @param aluno_id
	 * @param sala_id
	 * @param data
	 * @throws InvalidIdException
	 * @throws EmptyFieldsException
	 */
	@Transactional
	public void addDefesaFinalPresencial(Long docente_id, Long arguente_id, Long presidente_id,
			Long aluno_id, Long sala_id, LocalDateTime data) throws InvalidIdException, EmptyFieldsException {
		addDefesa(docente_id, arguente_id, presidente_id, aluno_id, null, sala_id, data, true, 90);
	}

	/**
	 * Marca uma defesa de tese presencial
	 * @param docente_id
	 * @param arguente_id
	 * @param presidente_id
	 * @param aluno_id
	 * @param link
	 * @param data
	 * @throws InvalidIdException
	 * @throws EmptyFieldsException
	 */
	@Transactional
	public void addDefesaPresencial(Long docente_id, Long arguente_id, Long aluno_id, Long sala_id, LocalDateTime data) throws InvalidIdException, EmptyFieldsException {
		addDefesa(docente_id, arguente_id, null, aluno_id, null, sala_id, data, false, 60);
	}

	/**
	 * Marca uma defesa de tese online ou presencial para a data data, sendo o 
	 * orientador o docente com o docenteId e o arguente o docente com o
	 * id aguente_id
	 * @param docenteId
	 * @param arguente_id
	 * @param presidente_id
	 * @param alunoId
	 * @param link
	 * @param sala_id
	 * @param data
	 * @param isFinal
	 * @param duration
	 * @throws InvalidIdException
	 * @throws EmptyFieldsException
	 */
	@Transactional
	private void addDefesa(Long docenteId, Long arguente_id, Long presidente_id, Long alunoId, 
			String link, Long sala_id, LocalDateTime data, boolean isFinal, int duration) 
					throws InvalidIdException, EmptyFieldsException {

		//procura um docente com o id docenteId (orientador)
		Optional<Docente> possivelOrientador = docenteRepository.findById(docenteId);
		if (!possivelOrientador.isPresent()) {
			throw new InvalidIdException("There is no Docente with this orientador_id");
		}
		//procura um docente com o id aguente_id(arguente)
		Optional<Docente> possivel_arguente = docenteRepository.findById(arguente_id);
		if (!possivel_arguente.isPresent()) {
			throw new InvalidIdException("There is no Docente with this arguente_id");
		}

		//procura um aluno com o id alunoId
		Optional<Aluno> possivelAluno = alunoRepository.findById(alunoId);
		if (!possivelAluno.isPresent()) {
			throw new InvalidIdException("There is no Aluno with this id");
		}

		Aluno aluno = possivelAluno.get();
		Docente orientador = possivelOrientador.get();
		Docente arguente = possivel_arguente.get();
		DefesaProposta defProp;

		if (link != null && sala_id == null) {

			//defesa online 
			defProp = new DefesaProposta(aluno.getPropostasTese().get(aluno.getPropostasTese().size() -1), link, data, orientador, arguente);

		} else if (link == null && sala_id != null) {

			//defesa presencial
			Optional<Sala> possivelSala = salaRepository.findById(sala_id);

			if (!possivelSala.isPresent()) {
				throw new InvalidIdException("There is no Sala with this id");
			}

			//Cria sala e associa a defesa a mesma
			Sala sala = possivelSala.get();
			defProp = new DefesaProposta(aluno.getPropostasTese().get(0), sala, data, orientador, arguente);

			sala.addDefesa(defProp);
			salaRepository.save(sala);

		} else {
			throw new InvalidIdException("You must provide either a link or a sala_id, but not both.");
		}		

		if (isFinal) {
			Optional<Docente> possivel_presidente = docenteRepository.findById(presidente_id);
			if (!possivel_presidente.isPresent()) {
				throw new InvalidIdException("Theres is no docente with this president_id");
			}

			//no caso de ser final o presidente é que avalia
			Docente presidente = possivel_presidente.get();
			presidente.updateAgenda(data, duration);
			presidente.addDefesaAAvaliar(defProp);
			docenteRepository.save(presidente);

			defProp.setPresidente(presidente);

		}else {

			//caso contrario o orientador eh que avalia
			orientador.addDefesaAAvaliar(defProp);
		}

		orientador.updateAgenda(data, duration);
		docenteRepository.save(orientador);

		arguente.updateAgenda(data, duration);
		docenteRepository.save(arguente);

		aluno.addDefPropADefender(defProp);
		alunoRepository.save(aluno);

		defesaPropostaRepository.save(defProp);

		// Sincronizar com o banco de dados
		defesaPropostaRepository.flush();
		docenteRepository.flush();
		alunoRepository.flush();
	}

	public List<DefesaPropostaDTO> getDefesasFinaisAAvaliar(long idPresidente) throws InvalidIdException{
		return getDefesasAAvaliar(idPresidente, true);
	}
	
	@Transactional(readOnly = true)
	public List<DefesaPropostaDTO> getDefesasAAvaliar(long idPresidente, boolean isFinal) throws InvalidIdException{
		List<DefesaProposta> defesasProposta = new ArrayList<>();
		Optional<Docente> presidente = docenteRepository.findById(idPresidente);

		if(presidente.isPresent()) {
			for (DefesaProposta defesaProposta : presidente.get().getDefesaAAvaliar()) {
				if(defesaProposta.isDefesaFinal() == isFinal) {
					defesasProposta.add(defesaProposta);
				}
			}
		}else {
			throw new InvalidIdException("presidente not found");
		}
		return defesasProposta.stream().map(DocenteHandler::dtofyDefesaProposta).toList();
	}

	@Transactional
	public void registarNotaDefesa(Long idPresidente, Long idDefesaFinal, int nota) throws DuplicateActionException, BadValueException, InvalidIdException {
		Optional<DefesaProposta> defesa_potencial = defesaPropostaRepository.findById(idDefesaFinal);

		Optional<Docente> presidente_potencial = docenteRepository.findById(idPresidente);

		if (!presidente_potencial.isPresent()) {
			throw new InvalidIdException("Docente not found");
		}

		if (!defesa_potencial.isPresent()) {
			throw new InvalidIdException("defesa final not found");
		}

		if(nota <= 0) {
			throw new BadValueException("nota must be positive");
		}

		if(defesa_potencial.get().getNota() != 0) {
			throw new DuplicateActionException("esta nota ja foi avaliada");
		}

		Docente presidente = presidente_potencial.get();
		DefesaProposta defesa = defesa_potencial.get();

		defesa.setNota(nota);
		presidente.delDefesaAAvaliar(defesa);

		defesaPropostaRepository.save(defesa);
		docenteRepository.save(presidente);

		// Sincronizar com o banco de dados
		defesaPropostaRepository.flush();
		docenteRepository.flush();
	}


	private static DefesaPropostaDTO dtofyDefesaProposta(DefesaProposta dPropFinal) {
		DefesaPropostaDTO dPropFinalDTO = new DefesaPropostaDTO();
		dPropFinalDTO.setId(dPropFinal.getId());
		return dPropFinalDTO;
	}

	public List<String> getMestrados() {
		List<String> listaMestrados = new ArrayList<>();
		for (Mestrados mestrado : Mestrados.values()) {
			listaMestrados.add(mestrado.toString());
		}
		return listaMestrados;
	}



	/**
	 * Submete um tema atraves de um docente
	 * @param idDocenteCorrente
	 * @param titulo
	 * @param descricao
	 * @param mestrados
	 * @param renumeracao
	 * @param idAnoLectivo
	 * @throws EmptyFieldsException
	 * @throws BadValueException
	 * @throws InvalidIdException
	 */
	public void submeterTemaDocente(Long idDocenteCorrente, String titulo, String descricao, List<String> mestrados, float renumeracao, Long idAnoLectivo) throws EmptyFieldsException, BadValueException, InvalidIdException {

		submeterTema(null, titulo, descricao, mestrados, renumeracao, idAnoLectivo, idDocenteCorrente, false);
	}

	/**
	 * Cria um novo tema e adicona a lista de atemas a orientar do orientador interno
	 * @param utilEmp_id
	 * @param titulo
	 * @param descricao
	 * @param possiveis_mestrados
	 * @param remuneracao
	 * @param anoLetivoId
	 * @param orinetador_id
	 * @param isEmpresarial
	 * @throws EmptyFieldsException
	 * @throws BadValueException
	 * @throws InvalidIdException
	 */
	@Transactional
	private void submeterTema(Long utilEmp_id, String titulo, String descricao, List<String> possiveis_mestrados, float remuneracao, Long anoLetivoId, Long orinetador_id, boolean isEmpresarial) throws EmptyFieldsException, BadValueException, InvalidIdException {

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

		//guarda o tema na lista de temas a orientar
		orientador_interno.addTemaAOrientar(tema);
		docenteRepository.save(orientador_interno);

		al.addTema(tema);
		anoLectivoRepository.save(al);

		//atualizar a base de dados
		temaRepository.flush();
		docenteRepository.flush();
		anoLectivoRepository.flush();

	}

	/*
	 * Converts Docente to DocenteDTO
	 */
	private static DocenteDTO dtofyDocente(Docente a) {
		DocenteDTO a2 = new DocenteDTO();
		a2.setId(a.getId());
		a2.setNome(a.getNome());
		a2.setApelido(a.getApelido());
		a2.setNDocente(a.getNdocente());
		a2.setAgenda(a.getAgenda());
		return a2;
	}

	/*
	 * Converts Aluno to AlunoDTO
	 */
	private static AlunoDTO dtofyAluno(Aluno a) {
		AlunoDTO a2 = new AlunoDTO();
		a2.setId(a.getId());
		a2.setNome(a.getNome());
		a2.setApelido(a.getApelido());
		a2.setnAluno(a.getnAluno());
		a2.setMestrado(a.getMestrado());
		a2.setMedia(a.getMedia());
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
	
	@Transactional(readOnly = true)
	public DocenteDTO getDocente(String username) {
		return dtofyDocente(docenteRepository.findByNDocente(username));
	}
	
	@Transactional(readOnly = true)
	public List<TemaDTO> getTemasSubmetidos(Long idUtilEmpCorrente) throws InvalidIdException {
		return docenteRepository.findById(idUtilEmpCorrente)
				.orElseThrow(() -> new InvalidIdException("Docente not found with id: " + idUtilEmpCorrente))
				.getTemasPropostos()
				.stream()
				.map(DocenteHandler::dtfyTemas)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public List<PropostaTeseDTO> getPropostaTese(Long docenteID) throws InvalidIdException {
		return docenteRepository.findById(docenteID)
				.orElseThrow(() -> new InvalidIdException("Docente not found with id: " + docenteID))
				.getAlunosAOrientar().stream()
				.flatMap(aluno -> aluno.getPropostasTese().stream())
				.map(this::dtofyPropostaTese) // Assumindo que dtofy é um método na mesma classe
				.collect(Collectors.toList());
	}

	private PropostaTeseDTO dtofyPropostaTese(PropostaTese propostaTese) {
		PropostaTeseDTO p = new PropostaTeseDTO();
		p.setAluno(dtofyAluno(propostaTese.getAluno()));
		p.setDocumento(propostaTese.getDocumento());
		p.setId(propostaTese.getId());
		return p;
	}
	
	private static SalaDTO dtofySala(Sala sala) {
		SalaDTO s = new SalaDTO();
		s.setDatasEHorasMarcadas(sala.getDatasEHorasMarcadas());
		s.setNumero(sala.getNumero());
		s.setId(sala.getId());
		return s;
	}
	
	@Transactional(readOnly = true)
	public List<DocenteDTO> getDocentesComExcecao(Long idDocenteCorrente, LocalDateTime data, int duracao) {
		return docenteRepository.findAll()
				.stream()
				.filter(docente -> !docente.getId().equals(idDocenteCorrente) && docente.isAvailable(data, data.plusMinutes(duracao)))
				.map(DocenteHandler::dtofyDocente).toList();
	}
	
	@Transactional(readOnly = true)
	public List<SalaDTO> getSalasDisponiveis(LocalDateTime data, int duracao) {
		return salaRepository.findAll()
				.stream()
				.filter(sala -> sala.isAvailable(data, data.plusMinutes(duracao)))
				.map(DocenteHandler::dtofySala)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public List<AlunoDTO> getAlunosAOrientarComProposta(Long idDocenteCorrente) throws InvalidIdException {
		return docenteRepository.findById(idDocenteCorrente)
				.orElseThrow(() -> new InvalidIdException("Docente not found with id: " + idDocenteCorrente))
				.getAlunosAOrientar()
				.stream()
				.filter(aluno -> aluno.getPropostasTese().size() != 0)
				.map(DocenteHandler::dtofyAluno)
				.toList();
	}
	
	@Transactional(readOnly = true)
	public long getDocenteId(String username) {
		return docenteRepository.findByNDocente(username).getId();
	}

	public List<AlunoDTO> getAlunos() {
		return alunoRepository.findAll().stream().map(DocenteHandler::dtofyAluno).toList();
	}
}
