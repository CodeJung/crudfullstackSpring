package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> ListarCursos() {
        return cursoRepository.findAll();
    }

    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, Curso novoCurso) {
        return cursoRepository.findById(id).map(c -> {
            c.setNome(novoCurso.getNome());
            c.setCargaHoraria(novoCurso.getCargaHoraria());
            c.setAtivo(novoCurso.isAtivo());
            return cursoRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado com id " + id));
    }

    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }
}
