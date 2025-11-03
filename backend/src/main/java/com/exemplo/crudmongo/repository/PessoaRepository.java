package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository; remover esse código
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    // Método personalizado para buscar pessoas por nome
    List<Pessoa> findByNome(String nome);
    List<Pessoa> findByIdade(int idade);
    List<Pessoa> findByNomeContainingIgnoreCaseAndIdade(String nome, int idade);
}


