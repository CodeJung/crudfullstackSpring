package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

    List<Pessoa> findByIdade(Integer idade);

    Page<Pessoa> findAll(Pageable pageable);
}


