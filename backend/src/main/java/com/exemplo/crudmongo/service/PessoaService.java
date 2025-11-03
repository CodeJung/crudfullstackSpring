package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.DTOS.RelatorioDTO;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.exception.exceptions.UserNotFound;
import com.exemplo.crudmongo.repository.CursoRepository;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
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
        if (id == 0) {
            throw new UserNotFound("Usário não encontrado. ID do usuário não pode ser igual a zero.");
        }

        if (id < 0) {
            throw new UserNotFound("Usuário não encontrado. ID do usuário não pode ser negativo.");
        }

        return pessoaRepository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            return pessoaRepository.save(p);
        }).orElseThrow(() -> new UserNotFound("Pessoa não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        if (id == 0) {
            throw new UserNotFound("Usário não encontrado. ID do usuário não pode ser igual a zero.");
        }

        if (id < 0) {
            throw new UserNotFound("Usuário não encontrado. ID do usuário não pode ser negativo.");
        }

        pessoaRepository.deleteById(id);
    }

    public List<Pessoa> buscarPessoaPorNome(String pessoaNome) {
        List<Pessoa> pessoasEncontradasPeloNome = pessoaRepository.findAll()
                .stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(pessoaNome.toUpperCase()))
                .collect(Collectors.toList());

        if (pessoasEncontradasPeloNome.isEmpty()) {
            throw new UserNotFound("Pessoas com nome de " + pessoaNome + " não foram encontradas.");
        }

        return pessoasEncontradasPeloNome;
    }

    public List<Pessoa> buscarPessoaPorIdade(int pessoaIdade) {
        List<Pessoa> pessoasEncontradasPelaIdade = pessoaRepository.findAll()
                .stream()
                .filter(p -> p.getIdade() == pessoaIdade)
                .collect(Collectors.toList());

        if (pessoasEncontradasPelaIdade.isEmpty()) {
            throw new UserNotFound("Pessoas com idade iguais a " + pessoaIdade + " não foram encontradas");
        }

        return pessoasEncontradasPelaIdade;
    }

    public List<Pessoa> buscarPessoasPorNomeDeCurso(String nome) {
        List<Pessoa> cursosEncontradosPeloNome = pessoaRepository.findAll()
                .stream()
                .filter(c -> c.getCurso()
                    .stream()
                    .anyMatch(nomeCurso -> nomeCurso.getNome().toUpperCase().contains(nome.toUpperCase())))
                .toList();

        if (cursosEncontradosPeloNome.isEmpty()) {
            throw new UserNotFound("Nenhum curso foi encontrado com o nome de " + nome);
        }

        return cursosEncontradosPeloNome;
    }

    public List<Pessoa> buscarPessoasPorNomeIdadeCurso(String nome, String curso, int idadeMin, int idadeMax) {
        List<Pessoa> pessoasEncontradasPorNomeIdadeCurso = pessoaRepository.findAll().
                stream()
                .filter(p -> p.getCurso().stream().anyMatch(c -> c.getNome().toUpperCase().contains(curso.toUpperCase())))
                .filter(p -> p.getNome().toUpperCase().contains(nome.toUpperCase()))
                .filter(p -> p.getIdade() >= idadeMin)
                .filter(p -> p.getIdade() <= idadeMax)
                .toList();

        if (pessoasEncontradasPorNomeIdadeCurso.isEmpty()) {
            throw new UserNotFound("Nenhuma pessoa foi encontrada baseado nesses critérios.");
        }

        return pessoasEncontradasPorNomeIdadeCurso;
    }

    public int totalPessoas() {
        List<Pessoa> total = pessoaRepository.findAll();

        if (total.isEmpty()) {
            throw new UserNotFound("Nenhuma pessoa foi encontrada");
        }

        return total.size();
    }

    public double mediaIdade() {
        int idadesSomadas = 0;
        List<Pessoa> total = pessoaRepository.findAll();

        if (total.isEmpty()) {
            throw new UserNotFound("Nenhuma pessoa foi encontrada");
        }

        for (Pessoa pessoa : total) {
            idadesSomadas += pessoa.getIdade();
        }

        double media = idadesSomadas / totalPessoas();

        return media;
    }

    public int totalPorCurso() {
        List<Curso> totalCurso = cursoRepository.findAll()
                .stream()
                .toList();

        if (totalCurso.isEmpty()) {
            throw new UserNotFound("Nenhuma pessoa foi achada com relação a algum curso.");
        }

        return totalCurso.size();
    }

    public RelatorioDTO relatorio() {
        List<RelatorioDTO> relatorioDTOList = pessoaRepository.findAll()
                .stream()
                .map(p -> new RelatorioDTO(totalPessoas(), totalPorCurso(), mediaIdade()))
                .toList();

        if (relatorioDTOList.isEmpty()) {
            throw new UserNotFound("Erro em gerar relatório, nenhuma pessoa foi encontrada.");
        }

        return relatorioDTOList.get(0);
    }

    public Page<Pessoa> paginarResultados(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }
}
