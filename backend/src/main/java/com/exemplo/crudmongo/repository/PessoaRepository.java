package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import Java.util.List;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, String> {
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

    List<Pessoa> findByIdade(int idade);
}


