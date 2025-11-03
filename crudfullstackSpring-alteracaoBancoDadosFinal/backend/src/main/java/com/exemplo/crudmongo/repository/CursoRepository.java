package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Busca cursos pelo nome exato
    List<Curso> findByNome(String nome);

    // Busca cursos pela carga horária
    List<Curso> findByCargaHoraria(Integer cargaHoraria);

    // Busca cursos pelo status ativo/inativo
    List<Curso> findByAtivo(Boolean ativo);
}
