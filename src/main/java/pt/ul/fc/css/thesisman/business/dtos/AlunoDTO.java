package pt.ul.fc.css.thesisman.business.dtos;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.thesisman.business.enums.Mestrados;
@Component
public class AlunoDTO {
	private Long id;

	private String nome;

	private String apelido;

	private String nAluno;

	private Mestrados mestrado;

	private float media;

	private TemaDTO tema;

	public AlunoDTO() {
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

	public String getnAluno() {
		return nAluno;
	}

	public void setnAluno(String nAluno) {
		this.nAluno = nAluno;
	}

	public Mestrados getMestrado() {
		return mestrado;
	}

	public void setMestrado(Mestrados mestrado) {
		this.mestrado = mestrado;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}

	public TemaDTO getTema() {
		return tema;
	}

	public void setTema(TemaDTO tema) {
		this.tema = tema;
	}
}
