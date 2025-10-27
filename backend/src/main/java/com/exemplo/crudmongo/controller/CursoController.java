package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // ROLE_ALUNO e ROLE_COORDENADOR podem listar
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public List<Curso> listar() {
        return service.listarTodos();
    }

    // Endpoint para retornar usuário logado + role
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public UserInfo usuarioLogado(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return new UserInfo(username, roles);
    }

    // Apenas ROLE_COORDENADOR pode criar
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    // Apenas ROLE_COORDENADOR pode atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    // Apenas ROLE_COORDENADOR pode excluir
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    // Classe interna para retornar info do usuário logado
    public static class UserInfo {
        private String username;
        private String roles;

        public UserInfo(String username, String roles) {
            this.username = username;
            this.roles = roles;
        }

        public String getUsername() {
            return username;
        }

        public String getRoles() {
            return roles;
        }
    }
}
