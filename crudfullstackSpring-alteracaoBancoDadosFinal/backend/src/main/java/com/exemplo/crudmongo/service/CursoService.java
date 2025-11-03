package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.stereotype.Service;


//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Curso.
 */
@Service
public class CursoService {

    //@Autowired
     private final CursoRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório CursoRepository via construtor.
     */
    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }
    //--------------------------------------------------------------------------------

    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Curso> listarTodos() {
        return repository.findAll();
    }

        public List<Curso> buscarPorNome(String nome) {
            return repository.findByNome(nome);
        }

        public Curso buscarPorId(long id) {
            return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id));
        }
    
        // Integer estava errdo porque estava escrito errado.
        public List<Curso> buscarPorCargaHoraria(Integer CargaHoraria) {
            return repository.findByCargaHoraria(CargaHoraria);
        }
    
        public List<Curso> buscarPorAtivo(Boolean ativo) {
            return repository.findByAtivo(ativo);
        }

    //--------------------------------------------------------------------------------
    // 🔹 Criar novo curso
    public Curso salvar(Curso curso) {
        if (curso.getAtivo() == null) {
            curso.setAtivo(true);
        }
        return repository.save(curso);
    }
    // Criar novo curso (método públic para controller)
    public Curso criar(Curso curso) {
        return salvar(curso);
    }
    /**
     * Atualiza uma curso existente pelo ID.
     * @param id Identificador da curso a ser atualizada
     * @param novoCurso Dados atualizados do curso
     * @return Curso atualizada
     */      
    public Curso atualizar(Long id, Curso novoCurso) {
        Curso cursoExistente = buscarPorId(id);
        cursoExistente.setNome(novoCurso.getNome());
        cursoExistente.setCargaHoraria(novoCurso.getCargaHoraria());
        if (novoCurso.getAtivo() != null) {
            cursoExistente.setAtivo(novoCurso.getAtivo());
        }
        return repository.save(cursoExistente);
    }

    /**
     * Exclui um cusro pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
        
    public void deletar(Long id) {
        Curso curso = buscarPorId(id);
        boolean ativo;
        curso.setAtivo(ativo = false);
        repository.save(curso);
    }

}