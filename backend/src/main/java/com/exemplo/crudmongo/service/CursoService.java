package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Pessoa.
 */
@Service // Indica que esta classe é um serviço do Spring
public class CursoService {

    private final CursoRepository cursoRepository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório CursoRepository via construtor.
     */
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, Curso novoCurso) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNome(novoCurso.getNome());
                    curso.setCargaHoraria(novoCurso.getCargaHoraria());
                    curso.setAtivo(novoCurso.isAtivo());
                    return cursoRepository.save(curso);
                })
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id " + id));
    }

    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }
}
   