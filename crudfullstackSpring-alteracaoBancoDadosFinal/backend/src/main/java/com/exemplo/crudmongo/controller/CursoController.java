package com.exemplo.crudmongo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import java.util.List;

@RestController  // ✅ ADICIONADO - Indica que é um controller REST
@RequestMapping("/api/cursos")  // ✅ ADICIONADO - Define a rota base
public class CursoController {
    
        private final CursoService service;

        public CursoController(CursoService service) {
        this.service = service;
        }

    // LISTAR - permitido para ALUNO e COORDENADOR
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    @GetMapping
    public List<Curso> listar() {
        return service.listarTodos();
    }

    // BUSCAR POR ID - permitido para ALUNO e COORDENADOR
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // CRIAR - apenas COORDENADOR
    @PreAuthorize("hasRole('COORDENADOR')")
    @PostMapping
    public Curso criar(@RequestBody Curso curso) {
        return service.criar(curso);
    }

    // ATUALIZAR - apenas COORDENADOR
    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    // DELETAR - apenas COORDENADOR
    @PreAuthorize("hasRole('COORDENADOR')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}