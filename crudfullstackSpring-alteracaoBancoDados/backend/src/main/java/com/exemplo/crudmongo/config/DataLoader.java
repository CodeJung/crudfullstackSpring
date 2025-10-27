package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(CursoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 500; i++) { 
                    Curso curso = new Curso();
                    curso.setNome(faker.educator().course());
                    curso.setCargaHoraria(faker.number().numberBetween(800, 4000));
                    curso.setAtivo(faker.bool().bool());
                    repository.save(curso);
                }

                System.out.println("✅ Banco populado com 50 cursos!");
            } else {
                System.out.println("ℹ️ Banco já contém dados, não foi necessário repopular.");
            }
        };
    }
}
