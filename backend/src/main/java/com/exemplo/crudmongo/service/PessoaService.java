package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Pessoa.
 */
@Service
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
     *
     * @return Lista de pessoas
     */
    public List<Pessoa> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     *
     * @param pessoa Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     *
     * @param id        Identificador da pessoa a ser atualizada
     * @param novaPessoa Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Pessoa atualizar(@PathVariable Long id, Pessoa novaPessoa) {
        return repository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());

            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     *
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    /**
     * Busca pessoas filtrando por nome e idade, com paginação.
     *
     * @param nome     (opcional) nome para filtro
     * @param idade    (opcional) idade para filtro
     * @param pageable objeto Pageable para controle de paginação
     * @return página de pessoas que satisfazem os filtros
     */
    public Page<Pessoa> buscar(String nome, Integer idade, Pageable pageable) {
        return repository.buscarComFiltro(nome, idade, pageable);
    }
}

