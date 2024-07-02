package pt.ul.fc.css.thesisman.business.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.thesisman.business.entities.UtilEmpresarial;

public interface UtilEmpresarialRepository extends JpaRepository<UtilEmpresarial, Long>{
	@Query("SELECT a FROM UtilEmpresarial a WHERE a.nome LIKE %:q%")
	UtilEmpresarial findByName(@Param("q") String q);
	
    Optional<UtilEmpresarial> findByEmail(String email);

}