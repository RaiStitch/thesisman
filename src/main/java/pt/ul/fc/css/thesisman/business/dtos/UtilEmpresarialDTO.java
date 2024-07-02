package pt.ul.fc.css.thesisman.business.dtos;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class UtilEmpresarialDTO {
	
	private Long id;

	private String nome;

	private String apelido;

	private String empresa;
	
	private String email;
	
	private String password;

	private List<TemaDTO> temasSubmetidos;

	public UtilEmpresarialDTO() {
	}

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

	public List<TemaDTO> getTemasSubmetidos() {
		return temasSubmetidos;
	}

	public void setTemasSubmetidos(List<TemaDTO> temasSubmetidos) {
		this.temasSubmetidos = temasSubmetidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addTema(TemaDTO tema) {
		this.temasSubmetidos.add(tema);
	}
	
}
