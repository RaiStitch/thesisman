package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "tema")
public class Tema {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	private String titulo;
	
	@NonNull
	private String descricao;
	
	@NonNull
	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mestrados", joinColumns = @JoinColumn(name = "tema_id"))
	private List<Mestrados> mestrados;
	
	@NonNull
	private float renumeracao;
	
	@OneToOne
	@JoinColumn(name="aluno_id")
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "ano_lectivo_id")
	private AnoLectivo anoLectivo;
	
	public Tema(@NonNull String titulo, @NonNull String descricao, @NonNull List<Mestrados> mestrados, @NonNull float renumeracao, AnoLectivo anoLectivo) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.mestrados = mestrados;
		this.renumeracao = renumeracao;
		this.anoLectivo = anoLectivo;
	}

	public Tema() {
		mestrados = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Mestrados> getMestrados() {
		return this.mestrados;
	}

	public void setMestrados(List<Mestrados> mestrados) {
		this.mestrados = mestrados;
	}

	public float getRenumeracao() {
		return renumeracao;
	}

	public void setRenumeracao(float renumeracao) {
		this.renumeracao = renumeracao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public AnoLectivo getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(AnoLectivo anoLectivo) {
		this.anoLectivo = anoLectivo;
	}

	public void serAnoLectivo(AnoLectivo anoLectivo) {
		this.anoLectivo = anoLectivo;
	}
	
	public boolean isAssigned() {
		return this.aluno != null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descricao, id, renumeracao, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tema other = (Tema) obj;
		return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Float.floatToIntBits(renumeracao) == Float.floatToIntBits(other.renumeracao)
				&& Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Tema [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", mestrados=" + mestrados
				+ ", renumeracao=" + renumeracao + ", aluno=" + aluno + "]";
	}
	
}
