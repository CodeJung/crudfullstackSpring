package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping
    public Curso criar(@RequestBody Curso curso) {
        return service.salvarCurso(curso);
    }

    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id,
                           @RequestBody Curso curso) {
        return service.atualizarCurso(id, curso);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirCurso(id);
    }

    @GetMapping
    public List<Curso> listar() {
        return service.listarTodosCursos();
    }

    @GetMapping("/nome")
    public List<Curso> listarCursosPorNome(@RequestParam("valor") String nome) {
        return service.listarCursoPorNome(nome);
    }

    @GetMapping("/carga-horaria")
    public List<Curso> listarCursosPorCargaHoraria(@RequestParam("valor") double cargaHoraria) {
        return service.listarCursosPorCargaHoraria(cargaHoraria);
    }

    @GetMapping("/ativo")
    public List<Curso> listarCursosPorAtivo(@RequestParam("valor") boolean ativo) {
        return service.listarCursosPorAtivo(ativo);
    }

    @GetMapping("/pagina")
    public ResponseEntity<Page<Curso>> paginarResultados(@RequestParam(name = "numero") int pagina,
    @RequestParam(name = "tamanho") int tamanho) {
        if (pagina < 1) {
            throw new IllegalArgumentException("A página não pode ser menor que 1");
        }

        if (tamanho < 1) {
            throw new IllegalArgumentException("O tamanho da página não pode ser menor que 1");
        }

        Pageable cursoPageable = PageRequest.of(pagina - 1, tamanho);
        Page<Curso> cursoPage = service.paginarResultados(cursoPageable);

        return ResponseEntity.ok(cursoPage);
    }
}
