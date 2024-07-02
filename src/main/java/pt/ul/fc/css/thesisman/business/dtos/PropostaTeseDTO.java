package pt.ul.fc.css.thesisman.business.dtos;

import org.springframework.stereotype.Component;

@Component
public class PropostaTeseDTO {
	private Long id;
	
    private byte[] documento;
    
    private AlunoDTO aluno;
	
	
	public PropostaTeseDTO() {}


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
	 * @return the documento
	 */
	public byte[] getDocumento() {
		return documento;
	}


	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}


	/**
	 * @return the aluno
	 */
	public AlunoDTO getAluno() {
		return aluno;
	}


	/**
	 * @param aluno the aluno to set
	 */
	public void setAluno(AlunoDTO aluno) {
		this.aluno = aluno;
	}
}
