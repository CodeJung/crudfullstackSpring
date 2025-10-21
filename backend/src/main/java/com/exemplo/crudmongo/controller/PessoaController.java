package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
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

    @GetMapping("/nome")
    public List<Pessoa> buscarPorNome(@RequestParam("valor") String valor) {
        if (valor == null  valor.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro 'valor' é obrigatório");
        }
        return service.buscarPorNome(valor);
    }

    @GetMapping("/idade")
    public List<Pessoa> buscarPorIdade(@RequestParam("valor") Integer valor) {
        if (valor == null  valor < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro 'valor' deve ser um inteiro não negativo");
        }
        return service.buscarPorIdade(valor);
    }

    @GetMapping("/pagina")
    public Page<Pessoa> listarPaginado(@RequestParam("numero") Integer numero,
                                       @RequestParam("tamanho") Integer tamanho) {
        if (numero == null  numero < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro 'numero' deve ser >= 1");
        }
        if (tamanho == null  tamanho < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro 'tamanho' deve ser >= 1");
        }
        return service.listarPaginado(numero - 1, tamanho);
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
    public Pessoa atualizar(@PathVariable String id, 
    @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    /**
     * Exclui uma pessoa pelo ID.
     * Método acessível via DELETE em /pessoas/{id}
     * @param id Identificador da pessoa a ser excluída
     */
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }
}
