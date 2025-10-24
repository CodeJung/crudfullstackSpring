package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> listarTodosCursos(){
        return repository.findAll();
    }

    public Curso salvarCurso(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizarCurso(@PathVariable Long id, Curso curso) {
        return repository.findById(id).map(c -> {
            c.setNome(curso.getNome());
            c.setCargaHoraria(curso.getCargaHoraria());
            c.setAtivo(curso.getAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado."));
    }

    public void excluirCurso(Long id) {
        repository.deleteById(id);
    }

    public List<Curso> listarCursosPorNome(String nome) {
        List<Curso> cursosEncontradosPeloNome = repository.findAll()
                .stream()
                .filter(c -> c.getNome().toUpperCase().equals(nome.toUpperCase()))
                .collect(Collectors.toList());

        if (cursosEncontradosPeloNome.isEmpty()) {
            throw new RuntimeException("Nenhum curso foi encontrado com o nome de " + nome);
        }

        return cursosEncontradosPeloNome;
    }

    public List<Curso> listarCursosPorCargaHoraria(double cargaHoraria) {
        List<Curso> cursosEncontradosPelaCargaHoraria = repository.findAll()
                .stream()
                .filter(c -> c.getCargaHoraria() == cargaHoraria)
                .collect(Collectors.toList());

        if (cursosEncontradosPelaCargaHoraria.isEmpty()) {
            throw new RuntimeException("Nenhum curso foi encontrado com a carga horária de: " + cargaHoraria + "H");
        }

        return cursosEncontradosPelaCargaHoraria;
    }

    public List<Curso> listarCursosPorAtivo(boolean ativo) {
        List<Curso> cursosEncontradosPorAtivo = repository.findAll()
                .stream()
                .filter(c -> c.getAtivo() == ativo)
                .collect(Collectors.toList());

        if (cursosEncontradosPorAtivo.isEmpty()) {
            throw new RuntimeException("Nenhum curso encontrado.");
        }

        return cursosEncontradosPorAtivo;
    }
}
