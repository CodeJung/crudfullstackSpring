package com.exemplo.crudmongo.Model;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;   Remover esse código
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity  // Anotação JPA para indicar que esta classe é uma entidade
@Table(name = "pessoas") // Define o nome da tabela no banco de dados
// @Document(collection = "pessoa")   Remover esse código
public class Pessoa implements UserDetails {

    @Id // Indica que este campo é o identificador único do documento
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id ;
    private String nome;
    private String email;
    private String password;
    private int idade;

    private Role role;

    @ManyToMany
    @JoinTable(name = "aluno_curso",
    joinColumns = @JoinColumn(name = "aluno_id"),
    inverseJoinColumns = @JoinColumn(name = "curso_id"))
    Set<Curso> curso = new HashSet<>();

    public Pessoa() {
    }// Construtor padrão pois é necessário para o JPA e MongoDB funcionar corretamente

    public Pessoa(String email, String encryptedPassword, Role role) {
        this.email = email;
        this.password = encryptedPassword;
        this.role = role;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Curso> getCurso() {
        return curso;
    }

    public void setCurso(Set<Curso> curso) {
        this.curso = curso;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.COORDENADOR) return Set.of(new SimpleGrantedAuthority("ROLE_COORDENADOR"), new SimpleGrantedAuthority("ROLE_ALUNO"));
        else return Set.of(new SimpleGrantedAuthority("ROLE_ALUNO"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
