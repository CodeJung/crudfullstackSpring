package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Estende MongoRepository e usa String como tipo do ID
@Repository
public interface CursoRepository extends MongoRepository<Curso, String> {

    // Método para buscar apenas cursos ativos (para os alunos)
    List<Curso> findAllByAtivoTrue();
}