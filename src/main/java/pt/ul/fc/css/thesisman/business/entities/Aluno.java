package pt.ul.fc.css.thesisman.business.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;

@Entity
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String nome;
	@NonNull
	private String apelido;
	@NonNull
	private String nAluno;
	@Enumerated
	private Mestrados mestrado;
	@NonNull
	private float media;

	@ManyToOne
	@JoinColumn(name = "docenteId")
	private Docente orientador;

	@OneToOne(mappedBy = "aluno")
	private Tema tema;

	@ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(
	// name = "aluno_tema",
	// joinColumns = @JoinColumn(name = "aluno_id"),
	// inverseJoinColumns = @JoinColumn(name = "tema_id")
	// )
	private Set<Tema> temasEscolhidos;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "defesaProposta_id")
	private Set<DefesaProposta> defProps;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PropostaTese> propostasTese;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PropostaTese propostaTeseFinal;

	public Aluno(@NonNull String nome, @NonNull String apelido, @NonNull String nAluno, @NonNull Mestrados mestrado,
			@NonNull float media) {
		this.nome = nome;
		this.apelido = apelido;
		this.nAluno = nAluno;
		this.mestrado = mestrado;
		this.media = media;
		this.defProps = new HashSet<>();
		this.temasEscolhidos = new HashSet<>();
		this.propostasTese = new HashSet<>();
	}

	public Aluno() {
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

	public String getUsername() {
		return this.nAluno;
	}

	public void setUserName(String nAluno) {
		this.nAluno = nAluno;
	}

	public Mestrados getMestrado() {
		return mestrado;
	}

	public void setMestrado(String mestrado) {
		this.mestrado = Mestrados.valueOf(mestrado);
	}

	public String getnAluno() {
		return nAluno;
	}

	public void setnAluno(String nAluno) {
		this.nAluno = nAluno;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}

	public Tema getTema() {
		return this.tema;
	}

	public Set<Tema> getTemasEscolhidos() {
		return temasEscolhidos;
	}

	public void setTemasEscolhidos(Set<Tema> temasEscolhidos) {
		this.temasEscolhidos = temasEscolhidos;
	}

	public Docente getOrientador() {
		return orientador;
	}

	public void setOrientador(Docente orientadorInterno) {
		this.orientador = orientadorInterno;
	}

	public List<PropostaTese> getPropostasTese() {
		List<PropostaTese> propostasTese = new ArrayList<>();
		for (PropostaTese p : this.propostasTese) {
			propostasTese.add(p);
		}
		return propostasTese;
	}

	public void setPropostasTese(Set<PropostaTese> propostasTese) {
		this.propostasTese = propostasTese;
	}

	public PropostaTese getPropostaTeseFinal() {
		return propostaTeseFinal;
	}

	public void setPropostaTeseFinal(PropostaTese propostaTeseFinal) {
		this.propostaTeseFinal = propostaTeseFinal;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public void addDefPropADefender(DefesaProposta defProp) {
		this.defProps.add(defProp);
	}

	public void addTema(Tema tema) {
		temasEscolhidos.add(tema);
	}

	public void addPropostaTese(PropostaTese proposta) {
		this.propostasTese.add(proposta);
	}

	public void delTema(String tema) {
		for (Tema t : this.temasEscolhidos) {
			if (t.getTitulo().equals(tema)) {
				this.temasEscolhidos.remove(t);
			}
		}
	}

	public void delTema(Long temaID) {
		for (Tema t : this.temasEscolhidos) {
			if (t.getId() == temaID) {
				this.temasEscolhidos.remove(t);
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(apelido, id, media, mestrado, nAluno, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(apelido, other.apelido) && Objects.equals(id, other.id)
				&& Float.floatToIntBits(media) == Float.floatToIntBits(other.media) && mestrado == other.mestrado
				&& Objects.equals(nAluno, other.nAluno) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", apelido=" + apelido + ", nAluno=" + nAluno + ", mestrado="
				+ mestrado + ", media=" + media + ", orientador=" + orientador + ", tema=" + tema
				+ ", propostaTeseFinal=" + propostaTeseFinal + "]";
	}

	public Set<DefesaProposta> getDefesasADefender() {
		return this.defProps;
	}
}
