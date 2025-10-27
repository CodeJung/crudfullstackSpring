package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizar(Long id, Curso novoCurso) {
        return repository.findById(id).map(curso -> {
            curso.setNome(novoCurso.getNome());
            curso.setCargaHoraria(novoCurso.getCargaHoraria());
            curso.setAtivo(novoCurso.isAtivo());
            return repository.save(curso);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
