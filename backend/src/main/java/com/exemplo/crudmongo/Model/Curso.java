package com.exemplo.crudmongo.Model;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.Id;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome do curso é obrigatório")
    private String nome;
    @Positive(message = "A carga horária deve ser um valor positivo")
    private int cargaHoraria;
    private boolean ativo = true;

    public Curso() {
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getCargaHoraria() { return cargaHoraria; }
    public boolean isAtivo() { return ativo; }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
