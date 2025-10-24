package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List; 

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
     * Retorna as pessoas por nome cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Pessoa> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * busca pessoas por idade no banco de dados.
     * @param pessoa Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public List<Pessoa> buscarPorIdade(int idade) {
        return repository.findByIdade(idade);
    }

    public Page<Pessoa> buscarPorPagina(int numeroPagina, int tamanhoPagina) {

        Pageable pageable = PageRequest.of(numeroPagina - 1, tamanhoPagina);

        return repository.findAll(pageable);
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
}
