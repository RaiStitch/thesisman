package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.lang.NonNull;

@Entity
public class Sala {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull 
	private String numero;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "agenda_id")
	private List<Appointment> datasEHorasMarcadas;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_id")
	private Set<DefesaProposta> defesas;

	public Sala() {}

	public Sala(@NonNull String numero) {
		this.numero = numero;
		this.datasEHorasMarcadas = new ArrayList<>();
		this.defesas = new HashSet<>();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<LocalDateTime[]> getDatasEHorasMarcadas() {
		List<LocalDateTime[]> agenda = new ArrayList<>();
		for (Appointment a : this.datasEHorasMarcadas) {
			LocalDateTime[] aToCopy = {a.getStartTime(), a.getEndTime()};
			agenda.add(aToCopy);
		}
		return agenda;
	}

	public void setDatasEHorasMarcadas(List<Appointment> datasEHorasMarcadas) {
		this.datasEHorasMarcadas = datasEHorasMarcadas;
	}

	public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
		for (Appointment appointment : this.datasEHorasMarcadas) {

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

	@Override
	public int hashCode() {
		return Objects.hash(datasEHorasMarcadas, id, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		return Objects.equals(datasEHorasMarcadas, other.datasEHorasMarcadas) && Objects.equals(id, other.id)
				&& Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", numero=" + numero + ", datasEHorasMarcadas=" + datasEHorasMarcadas + "]";
	}

	public void addDefesa(DefesaProposta defProp) {
		this.defesas.add(defProp);
	}
}
