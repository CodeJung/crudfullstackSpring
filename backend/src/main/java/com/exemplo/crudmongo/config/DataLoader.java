package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;


import java.util.Locale;
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(PessoaRepository pessoaRepository) {
        return args -> {
            if (pessoaRepository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(18, 70));
                    pessoaRepository.save(pessoa);
                }


                System.out.println("✅ Banco populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco já contém dados, não foi necessário repopular.");
            }
        };
    }



    
}
