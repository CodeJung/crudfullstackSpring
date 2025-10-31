package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> { 

    List<Curso> findByNomeContainingIgnoreCase(String nome);


    List<Curso> findByAtivo(int ativo);
}