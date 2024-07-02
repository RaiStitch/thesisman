package pt.ul.fc.css.thesisman.business.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.business.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.business.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.business.entities.Aluno;
import pt.ul.fc.css.thesisman.business.entities.PropostaTese;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;
import pt.ul.fc.css.thesisman.business.exceptions.DuplicateActionException;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidIdException;
import pt.ul.fc.css.thesisman.business.exceptions.InvalidOptionException;
import pt.ul.fc.css.thesisman.business.exceptions.LoginErrorException;
import pt.ul.fc.css.thesisman.business.exceptions.NonExistentObjectException;
import pt.ul.fc.css.thesisman.business.exceptions.ThemeLimitException;
import pt.ul.fc.css.thesisman.business.repositories.AlunoRepository;
import pt.ul.fc.css.thesisman.business.repositories.PropostaTeseRepository;
import pt.ul.fc.css.thesisman.business.repositories.TemaRepository;

@Component
public class AlunoHandler {

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private TemaRepository temaRepository;

	@Autowired
	private PropostaTeseRepository propostaTeseRepository;

	public void loginAluno(String username, String password) throws EmptyFieldsException, LoginErrorException {
		if(username.isEmpty()) {
			throw new EmptyFieldsException("Name is a required field");
		}
		if(password.isEmpty()) {
			throw new EmptyFieldsException("Password is a required field");
		}
		if(alunoRepository.findByNAluno(username) == null) {
			throw new LoginErrorException("The username given does not exist"); 
		}
	}

	@Transactional(readOnly = true)
	public List<TemaDTO> getTemasDispComMestradoDeAluno(Long idAluno) throws NonExistentObjectException{
		List<TemaDTO> temasDTO = new ArrayList<TemaDTO>();
		if(temaRepository.count() > 0) {
			List<Tema> temas = new ArrayList<Tema>();
			temas = temaRepository.findTemasWithoutAluno();

			Optional<Aluno> aluno = alunoRepository.findById(idAluno);
			if(!aluno.isPresent()) {
				throw new NonExistentObjectException("aluno com o numero "+ idAluno + " inesistente");
			}
			//compara os mestrados de cada tema com o do aluno
			for (Tema tema : temas) {
				for (Mestrados mestrado : tema.getMestrados()) {
					if(mestrado.equals(aluno.get().getMestrado())) {
						temasDTO.add(dtofyTema(tema));
					}
				}
			}

		}
		return temasDTO;
	}

	@Transactional
	public void candidatarATema(Long idAlunoCorrente, Long idTema) throws ThemeLimitException, DuplicateActionException, InvalidOptionException {
		Optional<Aluno> alunoCorrente = alunoRepository.findById(idAlunoCorrente);


		if(alunoCorrente.isPresent()) {
			if(alunoCorrente.get().getTemasEscolhidos().size() >= 5) {
				throw new ThemeLimitException("o limite de temas escolhidos ja foi atingido");
			}else {
				Optional<Tema> temaEscolhido = temaRepository.findById(idTema);
				if(temaEscolhido.isPresent()) {
					if(alunoCorrente.get().getTemasEscolhidos().size() > 0) {

						for (Tema tema : alunoCorrente.get().getTemasEscolhidos()) {
							if(tema.equals(temaEscolhido.get())) {
								throw new DuplicateActionException("you've already choosed this theme");
							}
						}
					}
					alunoCorrente.get().addTema(temaEscolhido.get());
					alunoRepository.save(alunoCorrente.get());
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public List<AlunoDTO> getListaAlunosSemTema() {
		return alunoRepository.findAlunosWithoutTema().stream().map(AlunoHandler::dtofyAluno).toList();
	}

	@Transactional(readOnly = true)
	public List<TemaDTO> getListaTemasSemAluno() {
		return temaRepository.findTemasWithoutAluno().stream().map(AlunoHandler::dtofyTema).toList();
	}

	@Transactional(readOnly = true)
	public List<TemaDTO> getListaTemasEscolhidos(Long idAluno) {
		return alunoRepository.findById(idAluno).get().getTemasEscolhidos().stream().map(AlunoHandler::dtofyTema).toList();
	}

	@Transactional
	public void canCandidatura(Long idAluno, Long temaID) throws InvalidIdException {
		Optional<Aluno> aluno = alunoRepository.findById(idAluno);
		if (!aluno.isPresent()) {
			throw new InvalidIdException("O aluno com id " + idAluno + " nao existe");
		}

		aluno.get().delTema(temaID);
		alunoRepository.save(aluno.get());
	}

	@Transactional
	public void proporTese(byte[] documento, Long idAlunoCorrente) throws EmptyFieldsException, DuplicateActionException {
		if(documento.length == 0) {
			throw new EmptyFieldsException("documento is a required field");
		}
		Optional<Aluno> alunoCorrente = alunoRepository.findById(idAlunoCorrente);
		if(alunoCorrente.isPresent()) {
			if(alunoCorrente.get().getPropostasTese().size() > 0) {
				for (PropostaTese p : alunoCorrente.get().getPropostasTese()) {
					if(documentoIgual(p.getDocumento(), documento)) {
						throw new DuplicateActionException("o aluno ja propos esse documento");
					}
				}
			}
			PropostaTese proposta = new PropostaTese(documento, alunoCorrente.get());
			alunoCorrente.get().addPropostaTese(proposta);
			alunoRepository.save(alunoCorrente.get());
			propostaTeseRepository.save(proposta);
		}

	}

	@Transactional(readOnly = true)
	public float calcularTaxaSucesso() {
		int alunosBemSucedidos = 0;
		for (Aluno aluno : alunoRepository.findAll()) {
			if (aluno.getPropostaTeseFinal().getDefesaProposta().getNota() >= 9.5) {
				alunosBemSucedidos ++;
			}
		}
		return (alunosBemSucedidos / alunoRepository.count()) * 100;
	}

	//Compara os documentos
	private boolean documentoIgual(byte[] array1, byte[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
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

	/*
	 * Converts Tema to TemaDTO
	 */
	private static TemaDTO dtofyTema(Tema t) {
		TemaDTO tDTO = new TemaDTO();
		tDTO.setId(t.getId());
		tDTO.setTitulo(t.getTitulo());
		tDTO.setDescricao(t.getDescricao());
		tDTO.setMestrados(t.getMestrados().stream().map(Mestrados::name).toList());
		tDTO.setRemuneracao(t.getRenumeracao());
		return tDTO;
	}

	@Transactional(readOnly = true)
	public long getAlunoId(String nAluno) {
		return this.alunoRepository.findByNAluno(nAluno).getId();
	}
}
