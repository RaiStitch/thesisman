package pt.ul.fc.css.thesisman.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.thesisman.business.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	@Query("SELECT a FROM Aluno a WHERE a.nAluno LIKE %:f%")
	Aluno findByNAluno(@Param("f") String f);
	
	List<Aluno> findAllByOrderByMediaDesc();
	
	@Query("SELECT a FROM Aluno a LEFT JOIN a.tema t WHERE t IS NULL")
    List<Aluno> findAlunosWithoutTema();
	
	@Query("SELECT a FROM Aluno a LEFT JOIN a.orientador o WHERE o.id = :docenteId")
    List<Aluno> findAlunoByDocente(@Param("docenteId") Long docenteId);
}
