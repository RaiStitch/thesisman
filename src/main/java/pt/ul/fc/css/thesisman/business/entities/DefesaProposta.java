package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "defesa_proposta")
public class DefesaProposta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private String link;
	@NonNull 
	private float nota;
	@NonNull
	private boolean defesaFinal;
	@OneToOne
	@JoinColumn(name="propostaTese_id")
	private PropostaTese propostaTese;
	@ManyToOne
	@JoinColumn(name = "sala_id", nullable = true)
	private Sala sala;
	@ManyToOne
	private Docente orientador;
	@ManyToOne
	private Docente arguente;
	@ManyToOne
	private Docente presidente;
	@NonNull
	private LocalDateTime data;
	
	/**
	 * Cria um proposta de defesa com uma proposta de tese 
	 * @param propostaTese
	 * @param link
	 * @param data
	 * @param orientador
	 * @param arguente
	 */
	public DefesaProposta(PropostaTese propostaTese, String link, LocalDateTime data, Docente orientador, Docente arguente) {
		this.link = link;
		this.propostaTese = propostaTese;
		this.data = data;
		this.orientador = orientador;
		this.arguente = arguente;
		
	}
	
	public DefesaProposta(PropostaTese propostaTese) {
		this.propostaTese = propostaTese;
	}

	public DefesaProposta() {}

	public DefesaProposta(PropostaTese propostaTese, Sala sala, LocalDateTime data, Docente orientador, Docente arguente) {
		this.propostaTese = propostaTese;
		this.sala = sala;
		this.data = data;
		this.orientador = orientador;
		this.arguente = arguente;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public PropostaTese getPropostaTese() {
		return propostaTese;
	}

	public void setPropostaTese(PropostaTese propostaTese) {
		this.propostaTese = propostaTese;
	}
	
	public boolean isDefesaFinal() {
		return defesaFinal;
	}

	public void setDefesaFinal(boolean defesaFinal) {
		this.defesaFinal = defesaFinal;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, link, nota, propostaTese);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefesaProposta other = (DefesaProposta) obj;
		return Objects.equals(id, other.id) && Objects.equals(link, other.link)
				&& Float.floatToIntBits(nota) == Float.floatToIntBits(other.nota)
				&& Objects.equals(propostaTese, other.propostaTese);
	}

	@Override
	public String toString() {
		return "DefesaProposta [id=" + id + ", link=" + link + ", nota=" + nota + ", propostaTese=" + propostaTese
				+ "]";
	}

	public void setPresidente(Docente presidente) {
		this.presidente = presidente;
		
	}
}