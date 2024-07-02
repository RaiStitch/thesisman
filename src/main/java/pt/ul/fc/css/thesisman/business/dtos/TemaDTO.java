package pt.ul.fc.css.thesisman.business.dtos;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class TemaDTO {
	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	private List<String> mestrados;
	
	private float remuneracao;
	
	public TemaDTO() {}

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

	public List<String> getMestrados() {
		return mestrados;
	}

	public void setMestrados(List<String> mestrados) {
		this.mestrados = mestrados;
	}

	public float getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(float renumeracao) {
		this.remuneracao = renumeracao;
	}
}
