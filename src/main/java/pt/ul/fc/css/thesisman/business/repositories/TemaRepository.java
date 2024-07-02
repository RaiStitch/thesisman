package pt.ul.fc.css.thesisman.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.thesisman.business.entities.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long>{
	
	@Query("SELECT a FROM Tema a WHERE a.titulo LIKE %:q%")
	List<Tema> findByTitulo(@Param("q") String q);
	
	@Query("SELECT t FROM Tema t LEFT JOIN t.aluno a WHERE a IS NULL")
    List<Tema> findTemasWithoutAluno();
}
