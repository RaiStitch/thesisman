package pt.ul.fc.css.thesisman.business.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AnoLectivo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@NonNull
	private LocalDate dataInicio; 
	@NonNull
	private LocalDate dataFim;
	@NonNull
	private LocalDate dataAtribuicaoTemas;
	@NonNull
	private boolean ativo;
	
	@OneToMany(mappedBy = "anoLectivo", fetch = FetchType.EAGER)
	private Set<Tema> temas;
	
	public AnoLectivo() {
		this.temas = new HashSet<>();
		this.ativo = true;
	}
	
	public AnoLectivo(String descricao, LocalDate dataInicio, LocalDate dataFim) {
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.temas = new HashSet<>();
		this.ativo = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Set<Tema> getTemas() {
		return temas;
	}

	public void setTemas(Set<Tema> temas) {
		this.temas = temas;
	}
	
	public void addTema(Tema tema) {
		this.temas.add(tema);
	}
	
	public boolean removeTema(Tema tema) {
		return temas.remove(tema);
	}
	
	public void setDataAtribuicaoTemas(LocalDate data) {
		this.dataAtribuicaoTemas = data;	
	}
	
	public LocalDate getDataAtribuicaoTemas() {
		return this.dataAtribuicaoTemas;
	}
	
	public void updateStatus(boolean status) {
		this.ativo = status;
	}
	
	public boolean isAtivo() {
		return this.ativo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataFim, dataInicio, descricao, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnoLectivo other = (AnoLectivo) obj;
		return Objects.equals(dataFim, other.dataFim) && Objects.equals(dataInicio, other.dataInicio)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "AnoLectivo [id=" + id + ", descricao=" + descricao + ", dataInicio=" + dataInicio + ", dataFim="
				+ dataFim + ", temas=" + temas + "]";
	}
	
	
}
