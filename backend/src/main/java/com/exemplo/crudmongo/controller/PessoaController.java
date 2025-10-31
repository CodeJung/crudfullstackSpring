package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/pessoas") // Define o endpoint base para as requisições
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (CORS)
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

    @GetMapping("/nome")
    public List<Pessoa> buscarPessoasPorNome(@RequestParam("valor") String pessoaNome) {
        return service.buscarPessoaPorNome(pessoaNome);
    }

    @GetMapping("/idade")
    public List<Pessoa> buscarPessoasPorIdade(@RequestParam("valor") int pessoaIdade) {
        return service.buscarPessoaPorIdade(pessoaIdade);
    }

    @GetMapping("/cursos")
    public List<Pessoa> buscarPessoasPorCurso(@RequestParam("valor") String nome) {
        return service.listarPessoasPorNomeDeCurso(nome);
    }

    @GetMapping("/pagina")
    public ResponseEntity<Page<Pessoa>> paginarResultados(@RequestParam(name = "numero") int pagina, @RequestParam(name = "tamanho") int tamanho) {
        if (pagina < 1) {
            throw new IllegalArgumentException("A página não pode ser menor que 1");
        }

        if (tamanho < 1) {
            throw new IllegalArgumentException("O tamanho da página não pode ser menor que 1");
        }

        Pageable pageable = PageRequest.of(pagina - 1, tamanho);
        Page pagePessoa = service.paginarResultados(pageable);

        return ResponseEntity.ok(pagePessoa);
    }
}
