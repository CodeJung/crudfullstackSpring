package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository; remover esse código
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	// Busca por nome contendo (case-insensitive)
	java.util.List<Pessoa> findByNomeContainingIgnoreCase(String nome);

	// Busca por idade exata
	java.util.List<Pessoa> findByIdade(int idade);
}
