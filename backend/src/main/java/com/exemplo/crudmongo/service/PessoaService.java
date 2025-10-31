package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Pessoa.
 */
@Service // Indica que esta classe é um serviço do Spring
public class PessoaService {

    private final PessoaRepository repository; // Repositório para acesso ao banco de dados

    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Pessoa> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param pessoa Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novaPessoa Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Pessoa atualizar(@PathVariable Long id,  Pessoa novaPessoa) {
        return repository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<Pessoa> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O nome para busca não pode ser vazio.");
        }
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Pessoa> buscarPorIdade(Integer idade) {
        if (idade == null || idade <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A idade deve ser um número positivo.");
        }
        return repository.findByIdade(idade);
    }


    public Map<String, Object> listarPessoasPaginado(int numero, int tamanho) {
        if (numero < 1 || tamanho < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O número e o tamanho da página devem ser maiores ou iguais a 1.");
        }
        Pageable pageable = PageRequest.of(numero - 1, tamanho);
        Page<Pessoa> paginaPessoas = repository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("pessoas", paginaPessoas.getContent());
        response.put("paginaAtual", paginaPessoas.getNumber() + 1); 
        response.put("totalItens", paginaPessoas.getTotalElements());
        response.put("totalPaginas", paginaPessoas.getTotalPages());
        response.put("tamanhoPagina", paginaPessoas.getSize());

        return response;
    }
}

