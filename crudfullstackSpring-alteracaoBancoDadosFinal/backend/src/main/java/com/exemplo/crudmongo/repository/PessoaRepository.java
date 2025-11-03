package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import org.springframework.data.mongodb.repository.MongoRepository; remover esse código
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Busca todas as pessoas com nome exato (pode ser adaptado para LIKE)
    List<Pessoa> findByNome(String nome);
    
    // Busca todas as pessoas com idade igual à passada
    List<Pessoa> findByIdade(Integer idade);
}


