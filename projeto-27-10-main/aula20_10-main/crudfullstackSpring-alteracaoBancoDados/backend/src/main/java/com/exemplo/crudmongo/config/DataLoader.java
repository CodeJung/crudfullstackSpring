package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.PessoaRepository;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(PessoaRepository pessoaRepository, CursoRepository cursoRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("pt-BR"));

            // Popula Pessoas
            if (pessoaRepository.count() == 0) {
                int totalPessoas = 500; 
                for (int i = 0; i < totalPessoas; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(18, 70));
                    pessoaRepository.save(pessoa);
                }
                System.out.println("✅ Banco populado com " + totalPessoas + " pessoas!");
            } else {
                System.out.println("ℹ️ Banco já contém pessoas, não foi necessário repopular.");
            }

            // Popula Cursos
            if (cursoRepository.count() == 0) {
                int totalCursos = 50; 
                for (int i = 0; i < totalCursos; i++) {
                    Curso curso = new Curso();
                    curso.setNome(faker.educator().course());
                    curso.setCargaHoraria(faker.number().numberBetween(100, 600)); 
                    curso.setAtivo(faker.bool().bool());
                    cursoRepository.save(curso);
                }
                System.out.println("✅ Banco populado com " + totalCursos + " cursos!");
            } else {
                System.out.println("ℹ️ Banco já contém cursos, não foi necessário repopular.");
            }
        };
    }
}
