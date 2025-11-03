package com.exemplo.crudmongo.service;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CursoService {
    
    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public Curso criarCurso(Curso curso) {
        return repository.save(curso);
    }

    public List<Curso> listarCursos() {
        return repository.findAll();
    }

    public Curso atualizarCurso(@PathVariable Long id, Curso novoCurso) {
        return repository.findById(id).map(c -> {
            c.setNome(novoCurso.getNome());
            c.setCargaHoraria(novoCurso.getCargaHoraria());
            c.setAtivo(novoCurso.isAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }

    public void excluirCurso(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

}
