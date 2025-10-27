package com.exemplo.crudmongo.dto;

import com.exemplo.crudmongo.Model.Curso;
import lombok.Data;

@Data
public class CursoResponseDTO {
    private String id;
    private String nome;
    private Integer cargaHoraria;
    private boolean ativo;

    // Construtor para facilitar a conversão de Entidade para DTO
    public CursoResponseDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.cargaHoraria = curso.getCargaHoraria();
        this.ativo = curso.isAtivo();
    }
}