package com.exemplo.crudmongo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;

@Configuration
public class DataLoader{

    @Bean
    CommandLineRunner initDatabase(CursoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Curso("Direito", 300, false));
                repository.save(new Curso("Nutrição", 400, true));
                repository.save(new Curso("Medicina", 400, true));
            }
        };
    }
}
