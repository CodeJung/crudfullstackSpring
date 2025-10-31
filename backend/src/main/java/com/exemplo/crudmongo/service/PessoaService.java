package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.CursoRepository;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Pessoa.
 */
@Service // Indica que esta classe é um serviço do Spring
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository; // Repositório para acesso ao banco de dados

    @Autowired
    private CursoRepository cursoRepository;
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */

    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param pessoa Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novaPessoa Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Pessoa atualizar(@PathVariable Long id,  Pessoa novaPessoa) {
        return pessoaRepository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            return pessoaRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<Pessoa> buscarPessoaPorNome(String pessoaNome) {
        List<Pessoa> pessoasEncontradasPeloNome = pessoaRepository.findAll().stream().filter(
                pessoa -> pessoa.getNome().toUpperCase().contains(pessoaNome.toUpperCase()))
                .collect(Collectors.toList());

        if (pessoasEncontradasPeloNome.isEmpty()) {
            throw new RuntimeException("Pessoas com nome de " + pessoaNome + " não foram encontradas.");
        }

        return pessoasEncontradasPeloNome;
    }

    public List<Pessoa> buscarPessoaPorIdade(int pessoaIdade) {
        List<Pessoa> pessoasEncontradasPelaIdade = pessoaRepository.findAll()
                .stream()
                .filter(p -> p.getIdade() == pessoaIdade)
                .collect(Collectors.toList());

        if (pessoasEncontradasPelaIdade.isEmpty()) {
            throw new RuntimeException("Pessoas com idades iguais a " + pessoaIdade + "não foram encontradas");
        }

        return pessoasEncontradasPelaIdade;
    }

    public List<Pessoa> listarPessoasPorNomeDeCurso(String nome) {
        List<Pessoa> cursosEncontradosPeloNome = cursoRepository.findAll()
                .stream()
                .filter(c -> c.getNome().toUpperCase().contains(nome.toUpperCase()))
                .map(c -> c.getPessoa())
                .toList();

        if (cursosEncontradosPeloNome.isEmpty()) {
            throw new RuntimeException("Nenhum curso foi encontrado com o nome de " + nome);
        }

        return cursosEncontradosPeloNome;
    }

    public Page<Pessoa> paginarResultados(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }
}
