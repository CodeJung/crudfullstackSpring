package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.PessoaRepository;
import com.exemplo.crudmongo.repository.CursoRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(PessoaRepository pessoaRepository, CursoRepository cursoRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("pt-BR"));

            // 🔹 Popula tabela de Pessoas
            if (pessoaRepository.count() == 0) {
                for (int i = 0; i < 500; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(18, 70));
                    pessoaRepository.save(pessoa);
                }
                System.out.println("✅ Banco populado com 500 pessoas!");
            } else {
                System.out.println("ℹ️ Pessoas já existentes, não foi necessário repopular.");
            }

            // 🔹 Popula tabela de Cursos com 50 cursos
            if (cursoRepository.count() == 0) {
                for (int i = 1; i <= 50; i++) {
                    String nomeCurso = faker.educator().course() + " " + i; // nome aleatório de curso + número
                    int cargaHoraria = faker.number().numberBetween(1800, 4000); // carga horária aleatória
                    boolean ativo = faker.bool().bool(); // true ou false aleatório
                    Curso curso = new Curso(nomeCurso, cargaHoraria, ativo);
                    cursoRepository.save(curso);
                }
                System.out.println("✅ 50 cursos iniciais carregados no banco de dados!");
            } else {
                System.out.println("ℹ️ Cursos já existentes, não foi necessário repopular.");
            }
        };
    }
}
