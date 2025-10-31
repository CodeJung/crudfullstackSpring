package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'ALUNO')")
    public List<Curso> listar() {
        return cursoService.ListarCursos();
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso criar(@RequestBody Curso curso) {
        return cursoService.salvar(curso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return cursoService.atualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public void deletar(@PathVariable Long id) {
        cursoService.deletar(id);
    }


}
