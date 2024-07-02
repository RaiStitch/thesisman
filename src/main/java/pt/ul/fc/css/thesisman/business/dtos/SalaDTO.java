package pt.ul.fc.css.thesisman.business.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class SalaDTO {
	private Long id;

	private String numero;

	private List<LocalDateTime[]> datasEHorasMarcadas;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the datasEHorasMarcadas
	 */
	public List<LocalDateTime[]> getDatasEHorasMarcadas() {
		return datasEHorasMarcadas;
	}

	/**
	 * @param datasEHorasMarcadas the datasEHorasMarcadas to set
	 */
	public void setDatasEHorasMarcadas(List<LocalDateTime[]> datasEHorasMarcadas) {
		this.datasEHorasMarcadas = datasEHorasMarcadas;
	}


}
