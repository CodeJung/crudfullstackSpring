package com.exemplo.crudmongo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos") // Anotação para MongoDB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    private String id; 

    private String nome;

    private Integer cargaHoraria;

    private boolean ativo = true; // Lógica de "ativo" para soft-delete
}