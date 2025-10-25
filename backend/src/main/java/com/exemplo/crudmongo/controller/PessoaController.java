package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.Model.PaginacaoResponse;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
     * Busca por nome (contendo, case-insensitive).
     * Ex: GET /pessoas/nome?valor=ana
     */
    @GetMapping("/nome")
    public ResponseEntity<List<Pessoa>> buscarPorNome(@RequestParam(name = "valor") String valor) {
        List<Pessoa> resultado = service.buscarPorNome(valor);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Busca por idade exata.
     * Ex: GET /pessoas/idade?valor=30
     */
    @GetMapping("/idade")
    public ResponseEntity<?> buscarPorIdade(@RequestParam(name = "valor") String valor) {
        try {
            int idade = Integer.parseInt(valor);
            List<Pessoa> resultado = service.buscarPorIdade(idade);
            return ResponseEntity.ok(resultado);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body("Parâmetro 'valor' inválido para idade: deve ser um número inteiro.");
        }
    }

    /**
     * Paginação de resultados.
     * Ex: GET /pessoas/pagina?numero=1&tamanho=10  (numero é 1-based)
     */
    @GetMapping("/pagina")
    public ResponseEntity<?> pagina(@RequestParam(name = "numero") Integer numero,
                                    @RequestParam(name = "tamanho") Integer tamanho) {
        // Validações
        if (numero == null || tamanho == null) {
            return ResponseEntity.badRequest().body("Parâmetros 'numero' e 'tamanho' são obrigatórios.");
        }
        if (numero < 1) {
            return ResponseEntity.badRequest().body("Parâmetro 'numero' inválido: deve ser >= 1 (1 = primeira página).");
        }
        if (tamanho < 1) {
            return ResponseEntity.badRequest().body("Parâmetro 'tamanho' inválido: deve ser >= 1.");
        }

        // Converter numero (1-based) para página zero-based
    Page<Pessoa> page = service.buscarPagina(numero, tamanho);

        PaginacaoResponse resp = new PaginacaoResponse(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1, // retornar como 1-based para o cliente
                page.getSize()
        );

        return ResponseEntity.ok(resp);
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
}