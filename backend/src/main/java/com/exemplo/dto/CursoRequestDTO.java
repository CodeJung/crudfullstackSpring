package com.exemplo.crudmongo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @Positive(message = "A carga horária deve ser um número positivo")
    private Integer cargaHoraria;
}