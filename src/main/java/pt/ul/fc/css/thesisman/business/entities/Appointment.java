package pt.ul.fc.css.thesisman.business.entities;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@Column(name = "start_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;
	
	@NonNull
    @Column(name = "end_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;
	
	public Appointment() {}
    
	public Appointment(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * devolve o tempo inicial
	 * @return
	 */
	public LocalDateTime getStartTime() {
		return this.startTime;
	}
	
	/**
	 * devolve o tempo final
	 * @return
	 */
	public LocalDateTime getEndTime() {
		return this.endTime;
	}
	
	/**
	 * devolve o id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
}
