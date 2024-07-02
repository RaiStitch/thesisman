package pt.ul.fc.css.thesisman.business.entities;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class PropostaTese {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	@NonNull
    private byte[] documento;
	@ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
	@OneToOne(mappedBy = "propostaTese", cascade = CascadeType.ALL)
	private DefesaProposta defesaProposta;
	
	
	public PropostaTese() {}

	public PropostaTese(@NonNull byte[] documento, @NonNull Aluno aluno, DefesaProposta defesaProposta) {
		this.documento = documento;
		this.aluno = aluno;
		this.defesaProposta = defesaProposta;
	}
	
	public PropostaTese(@NonNull byte[] documento, @NonNull Aluno aluno) {
		this.documento = documento;
		this.aluno = aluno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public DefesaProposta getDefesaProposta() {
		return defesaProposta;
	}

	public void setDefesaProposta(DefesaProposta defesaProposta) {
		this.defesaProposta = defesaProposta;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(documento);
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropostaTese other = (PropostaTese) obj;
		return Arrays.equals(documento, other.documento) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "PropostaTese [id=" + id + ", documento=" + Arrays.toString(documento) + ", tema=" +  ", aluno="
				+ aluno + ", defesaProposta=" + defesaProposta + "]";
	}
}
