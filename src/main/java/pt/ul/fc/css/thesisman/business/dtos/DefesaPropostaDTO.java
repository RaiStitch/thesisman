package pt.ul.fc.css.thesisman.business.dtos;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class DefesaPropostaDTO {
	
	private Long id;

	private String link;
	
	private float nota;
	
	private boolean defesaFinal;
	
	private PropostaTeseDTO propostaTese;
	
	private SalaDTO sala;
	
	private DocenteDTO orientador;
	
	private DocenteDTO arguente;
	
	private DocenteDTO presidente;
	
	private LocalDateTime data;
	
	public DefesaPropostaDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PropostaTeseDTO getPropostaTese() {
		return propostaTese;
	}

	public void setPropostaTese(PropostaTeseDTO propostaTese) {
		this.propostaTese = propostaTese;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public boolean isDefesaFinal() {
		return defesaFinal;
	}

	public void setDefesaFinal(boolean defesaFinal) {
		this.defesaFinal = defesaFinal;
	}

	public SalaDTO getSala() {
		return sala;
	}

	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}

	public DocenteDTO getOrientador() {
		return orientador;
	}

	public void setOrientador(DocenteDTO orientador) {
		this.orientador = orientador;
	}

	/**
	 * @return the arguente
	 */
	public DocenteDTO getArguente() {
		return arguente;
	}

	/**
	 * @param arguente the arguente to set
	 */
	public void setArguente(DocenteDTO arguente) {
		this.arguente = arguente;
	}

	/**
	 * @return the presidente
	 */
	public DocenteDTO getPresidente() {
		return presidente;
	}

	/**
	 * @param presidente the presidente to set
	 */
	public void setPresidente(DocenteDTO presidente) {
		this.presidente = presidente;
	}

	/**
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	
}
