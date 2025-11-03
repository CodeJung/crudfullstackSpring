package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    public Curso criarCurso(@RequestBody Curso novoCurso) {
        return service.criarCurso(novoCurso);
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return service.listarCursos();
    }

    @PutMapping("/{id}")
    public Curso atualizarCurso(@PathVariable Long id,@RequestBody Curso cursoAtualizado) {
        return service.atualizarCurso(id, cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void excluirCurso(@PathVariable Long id) {
        service.excluirCurso(id);
    }
}