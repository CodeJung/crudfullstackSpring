package com.exemplo.crudmongo.Model;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;   Remover esse código
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity  // Anotação JPA para indicar que esta classe é uma entidade
@Table(name = "pessoas") // Define o nome da tabela no banco de dados
// @Document(collection = "pessoa")   Remover esse código
public class Pessoa implements UserDetails {

    @Id // Indica que este campo é o identificador único do documento
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id ;
    private String nome;
    private int idade;
   
    public Pessoa() {
    }// Construtor padrão pois é necessário para o JPA e MongoDB funcionar corretamente 

   
    public Long getId() {
        return id;
    }


//o return tava com erro,corrige//


  
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }



    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }



    @Override
    public boolean isEnabled() {
        return false;
    }
}
