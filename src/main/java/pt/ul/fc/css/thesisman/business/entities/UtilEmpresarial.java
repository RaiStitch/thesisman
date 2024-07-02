package pt.ul.fc.css.thesisman.business.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class UtilEmpresarial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String nome;
	@NonNull
	private String apelido;
	@NonNull
	private String empresa;
	
	@NonNull
	private String email;
	
	@NonNull
	private String password;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "utilEmp_id")
	private Set<Tema> temasSubmetidos;

	public UtilEmpresarial(@NonNull String nome, @NonNull String apelido, @NonNull String empresa, @NonNull String email, @NonNull String password) {
		super();
		this.nome = nome;
		this.apelido = apelido;
		this.empresa = empresa;
		this.email = email;
		this.password = password;
		this.temasSubmetidos = new HashSet<>();
	}

	public UtilEmpresarial() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Set<Tema> getTemasSubmetidos() {
		return temasSubmetidos;
	}

	public void setTemasSubmetidos(Set<Tema> temasSubmetidos) {
		this.temasSubmetidos = temasSubmetidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apelido, empresa, id, nome, password, temasSubmetidos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UtilEmpresarial other = (UtilEmpresarial) obj;
		return Objects.equals(apelido, other.apelido) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(password, other.password) && Objects.equals(temasSubmetidos, other.temasSubmetidos);
	}

	@Override
	public String toString() {
		return "UtilEmpresarial [id=" + id + ", nome=" + nome + ", apelido=" + apelido + ", empresa=" + empresa
				+ ", temasSubmetidos=" + temasSubmetidos + "]";
	}

	public void addTema(Tema tema) {
		this.temasSubmetidos.add(tema);
	}
	
}
