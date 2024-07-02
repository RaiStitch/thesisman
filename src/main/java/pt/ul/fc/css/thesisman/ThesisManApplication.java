package pt.ul.fc.css.thesisman;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.business.entities.Aluno;
import pt.ul.fc.css.thesisman.business.entities.AnoLectivo;
import pt.ul.fc.css.thesisman.business.entities.DefesaProposta;
import pt.ul.fc.css.thesisman.business.entities.Docente;
import pt.ul.fc.css.thesisman.business.entities.PropostaTese;
import pt.ul.fc.css.thesisman.business.entities.Sala;
import pt.ul.fc.css.thesisman.business.entities.Tema;
import pt.ul.fc.css.thesisman.business.entities.UtilEmpresarial;
import pt.ul.fc.css.thesisman.business.enums.Mestrados;
import pt.ul.fc.css.thesisman.business.repositories.AlunoRepository;
import pt.ul.fc.css.thesisman.business.repositories.AnoLectivoRepository;
import pt.ul.fc.css.thesisman.business.repositories.DefesaPropostaRepository;
import pt.ul.fc.css.thesisman.business.repositories.DocenteRepository;
import pt.ul.fc.css.thesisman.business.repositories.PropostaTeseRepository;
import pt.ul.fc.css.thesisman.business.repositories.SalaRepository;
import pt.ul.fc.css.thesisman.business.repositories.TemaRepository;
import pt.ul.fc.css.thesisman.business.repositories.UtilEmpresarialRepository;


@SpringBootApplication
public class ThesisManApplication {

	private static final Logger log = LoggerFactory.getLogger(ThesisManApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ThesisManApplication.class, args);
	}

    @Bean
    @Transactional
    CommandLineRunner populate(AnoLectivoRepository anoRep, DocenteRepository docenteRep,
                            AlunoRepository alunoRep, TemaRepository temaRep, UtilEmpresarialRepository utilEmpresarialRepository,
                            PropostaTeseRepository propostaTeseRep, DefesaPropostaRepository defesaPropostaRepository, SalaRepository salaRepository) {
		return (args) -> {
			List<Mestrados> mestrados = new ArrayList<>();
			for (Mestrados mestrado : Mestrados.values()) {
				mestrados.add(mestrado);
			}
			Tema tema1 = new Tema();
			tema1.setTitulo("titulo1");
			tema1.setDescricao("descricao");
			tema1.setMestrados(mestrados);
			tema1.setRenumeracao(1100);

			Tema tema2 = new Tema();
			tema2.setTitulo("titulo2");
			tema2.setDescricao("descricao2");
			tema2.setMestrados(mestrados);
			tema2.setRenumeracao(1107);

			Tema tema3 = new Tema();
			tema3.setTitulo("titulo3");
			tema3.setDescricao("descricao3");
			tema3.setMestrados(mestrados);
			tema3.setRenumeracao(1100);

			Tema tema4 = new Tema();
			tema4.setTitulo("titulo4");
			tema4.setDescricao("descricao4");
			tema4.setMestrados(mestrados);
			tema4.setRenumeracao(1100);

			Tema tema5 = new Tema();
			tema5.setTitulo("titulo5");
			tema5.setDescricao("descricao5");
			tema5.setMestrados(mestrados);
			tema5.setRenumeracao(1100);

			Tema tema6 = new Tema();
			tema6.setTitulo("titulo6");
			tema6.setDescricao("descricao");
			tema6.setMestrados(mestrados);
			tema6.setRenumeracao(1100);

			AnoLectivo anoLectivo = new AnoLectivo();

			anoLectivo.addTema(tema1);
			anoLectivo.addTema(tema2);
			anoLectivo.addTema(tema3);
			anoLectivo.addTema(tema4);
			anoLectivo.addTema(tema5);
			anoLectivo.addTema(tema6);

			anoLectivo.setDataInicio(LocalDate.of(2023, 10, 23));
			anoLectivo.setDataFim(LocalDate.of(2024, 7, 23));
			anoLectivo.setDataAtribuicaoTemas(LocalDate.now());
			anoRep.save(anoLectivo);

			tema1.setAnoLectivo(anoLectivo);
			tema2.setAnoLectivo(anoLectivo);
			tema3.setAnoLectivo(anoLectivo);
			tema4.setAnoLectivo(anoLectivo);
			tema5.setAnoLectivo(anoLectivo);
			tema6.setAnoLectivo(anoLectivo);

			anoRep.save(anoLectivo);
			anoRep.save(new AnoLectivo());
			anoRep.save(new AnoLectivo());
			anoRep.save(new AnoLectivo());

			temaRep.save(tema1);
			temaRep.save(tema2);
			temaRep.save(tema3);
			temaRep.save(tema4);
			temaRep.save(tema5);
			temaRep.save(tema6);

			log.info("tema1: " + tema1.toString());

			Aluno aluno1 = new Aluno("afonso", "aleluia", "fc56363", Mestrados.INFORMATICA, (float) 11.0);

			alunoRep.save(aluno1);
			alunoRep.save(new Aluno("luis", "santos", "fc56341", Mestrados.BIOESTATISTICA, (float) 13.0));
			alunoRep.save(new Aluno("pedro", "pinto", "fc56369", Mestrados.CIENCIA_COGNITIVA, (float) 18.0));
			alunoRep.save(new Aluno("andre", "alves", "fc56349", Mestrados.QUIMICA, (float) 19.2));

			utilEmpresarialRepository
			.save(new UtilEmpresarial("sr", "empresario", "microsoft", "empresario@gmail.com", "password"));
			utilEmpresarialRepository
			.save(new UtilEmpresarial("outro", "empresario", "linux", "outro@gmail.com", "password2"));

			byte[] documento = new byte[8];
			PropostaTese propostaTese = new PropostaTese(documento, aluno1);
			propostaTeseRep.save(propostaTese);
			byte[] documento2 = new byte[8];
			PropostaTese propostaTese2 = new PropostaTese(documento, aluno1);
			propostaTeseRep.save(propostaTese2);
			PropostaTese propostaTese3 = new PropostaTese(documento, aluno1);
			propostaTeseRep.save(propostaTese3);

			aluno1.addPropostaTese(propostaTese);
			alunoRep.save(aluno1);

			DefesaProposta defesaProposta1 = new DefesaProposta(propostaTese);
			defesaPropostaRepository.save(defesaProposta1);
			DefesaProposta defesaProposta2 = new DefesaProposta(propostaTese2);
			defesaPropostaRepository.save(defesaProposta2);
			DefesaProposta defesaProposta3 = new DefesaProposta(propostaTese3);
			defesaPropostaRepository.save(defesaProposta3);

			Docente docente = new Docente("eduardo", "n1", "fc123");
			docente.addTemaAOrientar(tema1);
			docente.addTemaAOrientar(tema2);
			docente.addTemaAOrientar(tema3);
			docente.addTemaAOrientar(tema4);
			docente.addTemaAOrientar(tema5);
			docente.addTemaAOrientar(tema6);

			docente.addAlunoAOrientar(aluno1);

			docente.addDefesaAAvaliar(defesaProposta1);
			docente.addDefesaAAvaliar(defesaProposta2);
			docente.addDefesaAAvaliar(defesaProposta3);


			docenteRep.save(docente);
			docenteRep.save(new Docente("salvador", "portugal", "fc124"));
			docenteRep.save(new Docente("ana", "sarzedas", "fc125"));
			docenteRep.save(new Docente("eulalio", "azevedo", "fc126"));
			docenteRep.save(new Docente("arsenio", "rodrigues", "fc127"));
			
			Sala sala = new Sala("1.2.26");
			salaRepository.save(sala);

			alunoRep.flush();
			docenteRep.flush();
			temaRep.flush();
			propostaTeseRep.flush();
			defesaPropostaRepository.flush();
			anoRep.flush();
			utilEmpresarialRepository.flush();
			salaRepository.flush();
		};
	}
}
