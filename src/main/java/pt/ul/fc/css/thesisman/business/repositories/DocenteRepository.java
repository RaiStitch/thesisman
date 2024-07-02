package pt.ul.fc.css.thesisman.business.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.thesisman.business.entities.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
	@Query("SELECT a FROM Docente a WHERE a.nDocente LIKE %:f%")
	Docente findByNDocente(@Param("f") String f);
	
	@Query("SELECT d FROM Docente d JOIN FETCH d.temasAOrientar t WHERE t.id = :temaid")
	Docente findDocenteByTema(@Param("temaid") Long temaid);


}