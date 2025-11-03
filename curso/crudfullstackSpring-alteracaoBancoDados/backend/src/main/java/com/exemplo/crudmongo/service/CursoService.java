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

    public Curso atualizar(Long id, Curso curso) {
        Curso existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        existente.setNome(curso.getNome());
        existente.setCargaHoraria(curso.getCargaHoraria());
        existente.setAtivo(curso.isAtivo());

        return repository.save(existente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}