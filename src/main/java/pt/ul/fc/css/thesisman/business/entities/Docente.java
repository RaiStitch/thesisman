package pt.ul.fc.css.thesisman.business.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Docente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull 
	private String nDocente;
	
	@NonNull 
	private String nome;
	
	@NonNull 
	private String apelido;
	
	private boolean admin;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "docente_id")
	private List<Tema> temasAOrientar;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "orientador")
	private List<Aluno> alunosAOrientar;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "docente_id")
	private List<DefesaProposta> defesaAAvaliar;
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "agenda_id")
	private List<Appointment> agenda;



	
	public Docente() {}

	public Docente(@NonNull String nome, @NonNull String apelido, @NonNull String nDocente) {
		this.nome = nome;
		this.apelido = apelido;
		this.nDocente = nDocente;
		this.temasAOrientar = new ArrayList<>();
		this.alunosAOrientar = new ArrayList<>();
		this.defesaAAvaliar = new ArrayList<>();
		this.agenda = new ArrayList<>();
	}

	public Docente(@NonNull String nome, @NonNull String apelido, @NonNull String nDocente, @NonNull boolean admin) {
		this.nome = nome;
		this.apelido = apelido;
		this.nDocente = nDocente;
		this.admin = admin;
		this.temasAOrientar = new ArrayList<>();
		alunosAOrientar = new ArrayList<>();
		defesaAAvaliar = new ArrayList<>();
		agenda = new ArrayList<>();
	}
	
	/**
	 * devolve o id
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Define o id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * devolve o nome
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * define o nome
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * devolve o apelido
	 * @return
	 */
	public String getApelido() {
		return apelido;
	}
	
	/**
	 * define o apelido
	 * @param apelido
	 */
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
	/**
	 * devolve o numero do docente
	 * @return
	 */
	public String getNdocente() {
		return nDocente;
	}
	
	/**
	 * define o numero do docente
	 * @param nDocente
	 */
	public void setNdocente(String nDocente) {
		this.nDocente = nDocente;
	}
	
	/**
	 * verifica se e administrador
	 * @return
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	/**
	 * define o estaco como administrador
	 * @param admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	/**
	 * devolve a lista de temas propostos
	 * @return
	 */
	public List<Tema> getTemasPropostos() {
		return temasAOrientar;
	}
	
	/**
	 * define a lista de temas propostos
	 * @param temasPropostos
	 */
	public void setTemasPropostos(List<Tema> temasPropostos) {
		this.temasAOrientar = temasPropostos;
	}	
	
	/**
	 * devolve a lista de alunos a orientar
	 * @return
	 */
	public List<Aluno> getAlunosAOrientar() {
		return alunosAOrientar;
	}
	
	/**
	 * define a lista de alunos a orientar
	 * @param alunosAOrientar
	 */
	public void setAlunosAOrientar(List<Aluno> alunosAOrientar) {
		this.alunosAOrientar = alunosAOrientar;
	}
	
	/**
	 * devolve a lista de defesas a avaliar
	 * @return
	 */
	public List<DefesaProposta> getDefesaAAvaliar() {
		return this.defesaAAvaliar;
	}
	
	/**
	 * define a lista de defesas a avaliar
	 * @param defesaAAvaliar
	 */
	public void setDefesaAAvaliar(List<DefesaProposta> defesaAAvaliar) {
		this.defesaAAvaliar = defesaAAvaliar;
	}
	
	/**
	 * devolve a agenda, lista com datas em que o docente esta ocupado
	 * @return
	 */
	public List<LocalDateTime[]> getAgenda() {
		List<LocalDateTime[]> agenda = new ArrayList<>();
		for (Appointment a : this.agenda) {
			LocalDateTime[] aToCopy = {a.getStartTime(), a.getEndTime()};
			agenda.add(aToCopy);
		}
		return agenda;
	}
	
	/**
	 * define a agenda
	 * @param agenda
	 */
	public void setAgenda(List<Appointment> agenda) {
		this.agenda = agenda;
	}
	
	/**
	 * adiciona um tema proposta a lista de temas a orientar
	 * @param tema
	 */
	public void addTemaAOrientar(Tema tema) {
		temasAOrientar.add(tema);
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin, agenda, alunosAOrientar, apelido, defesaAAvaliar, id, nDocente, nome,
				temasAOrientar);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Docente other = (Docente) obj;
		return admin == other.admin && Objects.equals(agenda, other.agenda)
				&& Objects.equals(alunosAOrientar, other.alunosAOrientar) && Objects.equals(apelido, other.apelido)
				&& Objects.equals(defesaAAvaliar, other.defesaAAvaliar) && Objects.equals(id, other.id)
				&& Objects.equals(nDocente, other.nDocente) && Objects.equals(nome, other.nome)
				&& Objects.equals(temasAOrientar, other.temasAOrientar);
	}

	@Override
	public String toString() {
		return "Docente [id=" + id + ", nDocente=" + nDocente + ", nome=" + nome + ", apelido=" + apelido + ", admin="
				+ admin + ", temasPropostos=" + temasAOrientar + ", alunosAOrientar=" + alunosAOrientar
				+ ", defesaAAvaliar=" + defesaAAvaliar + ", agenda=" + agenda + "]";
	}
	
	/**
	 * adiciona defesa a avaliar
	 * @param defProp
	 */
	public void addDefesaAAvaliar(DefesaProposta defProp) {
		this.defesaAAvaliar.add(defProp);
	}
	
	/**
	 * adiciona ocupacao na agenda
	 * @param data
	 */
	public void updateAgenda(LocalDateTime data, int duration) {
		this.agenda.add(new Appointment(data,  data.plusMinutes(duration)));
		
	}

	public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
		for (Appointment appointment : this.agenda) {
			
			//se start esta entre o comeco de um appointment e o final do mesmo 
			// ou se o end estiver entre o comeco de um appointment e o final do mesmo
			//entao entra em conflito
			if ((start.isAfter(appointment.getStartTime()) && start.isBefore(appointment.getEndTime()))
					|| (end.isAfter(appointment.getStartTime()) && end.isBefore(appointment.getEndTime()))) {
				return false;
			}
		}
		return true;
	}

	public void addAlunoAOrientar(Aluno aluno) {
		this.alunosAOrientar.add(aluno);
	}

	public void delDefesaAAvaliar(DefesaProposta defesaProposta) {
		this.defesaAAvaliar.remove(defesaProposta);		
	}
}
