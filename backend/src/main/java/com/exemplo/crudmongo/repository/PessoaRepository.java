package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p " +
           "WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
           "AND (:idade IS NULL OR p.idade = :idade)")
    Page<Pessoa> buscarComFiltro(@Param("nome") String nome,
                                 @Param("idade") Integer idade,
                                 Pageable pageable);
}


