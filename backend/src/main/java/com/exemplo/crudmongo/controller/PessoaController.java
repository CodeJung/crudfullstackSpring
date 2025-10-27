package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController 
@RequestMapping("/pessoas") 
@CrossOrigin(origins = "*") 
public class PessoaController {

    private final PessoaService service; 
    public PessoaController(PessoaService service) {
        this.service = service;
    }
    @GetMapping
    public List<Pessoa> listar() {
        return service.listarTodas();
    }

    /**
      Cria uma nova pessoa
      @param pessoa 
      @return 
     
    @PostMapping

      @param id //Identificador //
      @param pessoa //dados da pessoa//
      @return 
     
    @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    /**
      Exclui uma pessoa pelo ID.
      Método acessível via DELETE
     * @param id //pessoa a ser excluída//
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
