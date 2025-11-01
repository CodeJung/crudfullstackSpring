package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Listar - permitido para ROLE_ALUNO e ROLE_COORDENADOR
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO','COORDENADOR')")
    public List<Curso> listar() {
        return service.listarTodos();
    }

    // Buscar por id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ALUNO','COORDENADOR')")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Criar - somente ROLE_COORDENADOR
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Curso> criar(@RequestBody Curso curso) {
        Curso c = service.salvar(curso);
        return ResponseEntity.status(201).body(c);
    }

    // Atualizar - somente ROLE_COORDENADOR
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity.ok(service.atualizar(id, curso));
    }

    // Deletar - somente ROLE_COORDENADOR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
