package pt.ul.fc.css.thesisman.business.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.thesisman.business.entities.AnoLectivo;

public interface AnoLectivoRepository extends JpaRepository<AnoLectivo, Long> {
	
	@Query("SELECT a FROM AnoLectivo a WHERE (YEAR(a.dataInicio) = :ano OR YEAR(a.dataFim) = :ano) AND a.ativo = true")
    AnoLectivo findAnoLectivoAtivo(@Param("ano") int ano);

}

