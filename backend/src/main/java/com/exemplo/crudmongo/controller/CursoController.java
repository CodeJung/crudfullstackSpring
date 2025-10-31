package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/curso") // Define o endpoint base para as requisições
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (CORS)
@Validated
public class CursoController {

    private final CursoService service; // Serviço responsável pela lógica de negócio

    /**
     * Injeta o serviço PessoaService via construtor.
     */
    public CursoController(CursoService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * Método acessível via GET em /pessoas
     */
    @GetMapping
    public List<Curso> listar() {
        return service.listarTodos();
    }

    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * @param valor
     * Método acessível via GET em /pessoas
     */
    @GetMapping("/curso") 
    public ResponseEntity<List<Curso>> buscarPorNome(
            @RequestParam("valor") String nome 
    ) {
        List<Curso> nomeCursos = service.buscarPorCurso(nome);
        return ResponseEntity.ok(nomeCursos);
    }

    /**
     * Busca uma pela idade.
     * @param idade Idade a ser buscada
     * @return Lista de pessoas com a idade especificada
     */
    @GetMapping("/ativo")
    public ResponseEntity<List<Curso>> buscarCursoAtivo(
        @RequestParam("valor") int ativo
    ) {
        List<Curso> ativoCursos = service.buscarCursoAtivo(ativo);
        return ResponseEntity.ok(ativoCursos);
    }

    /**
     * Busca pessoas paginadas no banco de dados.
     * @param numero Número da página
     * @param tamanho Tamanho da página por pessoas
     * @return Página de pessoas
     */
    @GetMapping("/pagina")
    public ResponseEntity<Page<Curso>> buscarPorPagina(
            @RequestParam("numero") int numero,
            @RequestParam("tamanho") int tamanho
    ) {
        Page<Curso> pagina = service.buscarPorPagina(numero, tamanho);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Cria uma nova pessoa.
     * Método acessível via POST em /api
     * @param pessoa Objeto Pessoa recebido no corpo da requisição
     * @return Pessoa criada
     */
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')") 
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * Método acessível via PUT em /api/{id}
     * @param id Identificador da pessoa a ser atualizada
     * @param pessoa Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')") 
    public Curso atualizar(@PathVariable Long id, 
    @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    /**
     * Exclui uma pessoa pelo ID.
     * Método acessível via DELETE em /api/{id}
     * @param id Identificador da pessoa a ser excluída
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')") 
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
