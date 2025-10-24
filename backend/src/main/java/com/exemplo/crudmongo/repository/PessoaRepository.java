package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> { 

    List<Pessoa> findByNomeContainingIgnoreCase(String nome);


    List<Pessoa> findByIdade(int idade);
}