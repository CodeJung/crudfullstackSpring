package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.CursoRepository;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;


import java.util.Locale;
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(PessoaRepository repository, CursoRepository repository2) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));
                
                int totalPessoas = 150;
                for (int i = 0; i < totalPessoas; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(18, 300));
                    repository.save(pessoa);
                }

                System.out.println("✅ Banco populado com 150 pessoas!");
            } else {
                System.out.println("ℹ️ Banco já contém dados de pessoas, não foi necessário repopular.");
            }

            if (repository2.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));
                int totalCursos = 25;
                for (int i = 0; i < totalCursos; i++) {
                    Curso curso = new Curso();
                    curso.setNome(faker.educator().course());
                    curso.setCargaHoraria(faker.number().numberBetween(20, 500));
                    repository2.save(curso);
                }

                System.out.println("✅ Banco populado com 25 cursos!");
            } else {
                System.out.println("ℹ️ Banco já contém dados de cursos, não foi necessário repopular.");
            }
            
        };
    }



    
}
