package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/pessoas") // Define o endpoint base para as requisições
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (CORS)
@Validated
public class PessoaController {

    private final PessoaService service; // Serviço responsável pela lógica de negócio

    /**
     * Injeta o serviço PessoaService via construtor.
     */
    public PessoaController(PessoaService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * Método acessível via GET em /pessoas
     */
    @GetMapping
    public List<Pessoa> listar() {
        return service.listarTodas();
    }

    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * @param valor
     * Método acessível via GET em /pessoas
     */
    @GetMapping("/nome") 
    public ResponseEntity<List<Pessoa>> buscarPorNome(
            @RequestParam("valor") String nome 
    ) {

        List<Pessoa> nomePessoas = service.buscarPorNome(nome);
        
        return ResponseEntity.ok(nomePessoas);
    }

    /**
     * Busca uma pela idade.
     * @param idade Idade a ser buscada
     * @return Lista de pessoas com a idade especificada
     */
    @GetMapping("/idade")
    public ResponseEntity<List<Pessoa>> buscarPorIdade(
        @RequestParam("valor") int idade
    ) {
        List<Pessoa> idadePessoas = service.buscarPorIdade(idade);
        return ResponseEntity.ok(idadePessoas);
    }

    /**
     * Busca pessoas paginadas no banco de dados.
     * @param numero Número da página
     * @param tamanho Tamanho da página por pessoas
     * @return Página de pessoas
     */
    @GetMapping("/pagina")
    public ResponseEntity<Page<Pessoa>> buscarPorPagina(
            @RequestParam("numero") int numero,
            @RequestParam("tamanho") int tamanho
    ) {
        Page<Pessoa> pagina = service.buscarPorPagina(numero, tamanho);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Cria uma nova pessoa.
     * Método acessível via POST em /pessoas
     * @param pessoa Objeto Pessoa recebido no corpo da requisição
     * @return Pessoa criada
     */
    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return service.salvar(pessoa);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * Método acessível via PUT em /pessoas/{id}
     * @param id Identificador da pessoa a ser atualizada
     * @param pessoa Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, 
    @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    /**
     * Exclui uma pessoa pelo ID.
     * Método acessível via DELETE em /pessoas/{id}
     * @param id Identificador da pessoa a ser excluída
     */
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
