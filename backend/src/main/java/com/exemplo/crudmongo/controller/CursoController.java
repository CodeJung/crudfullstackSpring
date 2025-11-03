package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/cursos") // Define o endpoint base para as requisições
public class CursoController {
@Autowired
    private CursoRepository repository; // Serviço responsável pela lógica de negócio


    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * Método acessível via GET em /pessoas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public List<Curso> listar(){
        return repository.findAll();                        
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso criar(@RequestBody Curso curso) {
        return repository.save(curso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
   public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody Curso dadosAtualizados) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setNome(dadosAtualizados.getNome());
                    curso.setCargaHoraria(dadosAtualizados.getCargaHoraria());
                    curso.setAtivo(dadosAtualizados.isAtivo());
                    return ResponseEntity.ok(repository.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}