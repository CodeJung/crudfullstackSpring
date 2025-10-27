package com.exemplo.crudmongo.controller;

// import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;




import com.exemplo.crudmongo.Model.Pessoa;
// import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


// import java.util.List;

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

    @GetMapping("/buscar")
    public Page<Pessoa> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer idade,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        Pageable pageable = PageRequest.of(pagina, tamanho);
        return service.buscar(nome, idade, pageable);
    }

    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return service.salvar(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
