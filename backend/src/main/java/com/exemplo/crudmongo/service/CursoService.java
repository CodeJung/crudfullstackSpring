package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List; 

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Pessoa.
 */
@Service // Indica que esta classe é um serviço do Spring
public class CursoService {

    private final CursoRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    /**
     * Retorna as pessoas por nome cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Curso> buscarPorCurso(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * busca pessoas por idade no banco de dados.
     * @param curso Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public List<Curso> buscarCursoAtivo(int ativo) {
        return repository.findByAtivo(ativo);
    }

    public Page<Curso> buscarPorPagina(int numeroPagina, int tamanhoPagina) {

        Pageable pageable = PageRequest.of(numeroPagina - 1, tamanhoPagina);

        return repository.findAll(pageable);
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param curso Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novoCurso Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Curso atualizar(@PathVariable Long id, Curso novoCurso) {
        return repository.findById(id).map(c -> {
            c.setNome(novoCurso.getNome());
            c.setCargaHoraria(novoCurso.getCargaHoraria());
            c.setAtivo(novoCurso.getAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
