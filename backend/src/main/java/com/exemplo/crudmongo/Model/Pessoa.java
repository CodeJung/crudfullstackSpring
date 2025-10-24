package com.exemplo.crudmongo.Model;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;   Remover esse código
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


@Entity  // Anotação JPA para indicar que esta classe é uma entidade
@Table(name = "pessoas") // Define o nome da tabela no banco de dados
// @Document(collection = "pessoa")   Remover esse código
public class Pessoa {

    @Id // Indica que este campo é o identificador único do documento
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id ;
    private String nome;
    private int idade;

    public Pessoa() {
    }// Construtor padrão pois é necessário para o JPA e MongoDB funcionar corretamente 

    // Getter para o campo id
    public Long getId() {
        return id;
    }

    // Setter para o campo id
    public void setId(Long id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
