package pt.ul.fc.css.thesisman.business.dtos;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DocenteDTO {

	private Long id;
	
	private String nome;
	
	private String apelido;
	
	private String nDocente;
	
	private List<TemaDTO> temasAOrientar;
	
	private List<AlunoDTO> alunosAOrientar; 
	
	private List<PropostaDefesaDTO> defesaAAvaliar;
	
	private List<LocalDateTime[]> agenda;
	
	public DocenteDTO() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getApelido() {
		return apelido;
	}
	
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
	public List<TemaDTO> getTemasSubmetidos() {
		return temasAOrientar;
	}
	
	public void setTemasSubmetidos(List<TemaDTO> temasAOrientar) {
		this.temasAOrientar = temasAOrientar;
	}
	
	public void addTema(TemaDTO tema) {
		this.temasAOrientar.add(tema);
	}
	
	public List<AlunoDTO> getAlunosAOrientar(){
		return this.alunosAOrientar;
	}
	
	public void setAlunosAOrientar(List<AlunoDTO> alunosAOrientar) {
		this.alunosAOrientar = alunosAOrientar;
	}
	
	public List<PropostaDefesaDTO> getDefesasAAvaliar(){
		return this.defesaAAvaliar;
	}
	
	public void setDefesaAAvaliar(List<PropostaDefesaDTO> defesaAAvaliar) {
		this.defesaAAvaliar = defesaAAvaliar;
	}
	
	public List<LocalDateTime[]> getAgenda(){
		return this.agenda;
	}
	
	public void setAgenda(List<LocalDateTime[]> agenda) {
		this.agenda = agenda;
	}
	
	public void setNDocente(String nDocente) {
		this.setnDocente(nDocente);
	}

	/**
	 * @return the nDocente
	 */
	public String getnDocente() {
		return nDocente;
	}

	/**
	 * @param nDocente the nDocente to set
	 */
	public void setnDocente(String nDocente) {
		this.nDocente = nDocente;
	}

}
