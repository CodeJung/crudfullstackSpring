package com.exemplo.crudmongo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // ✅ ALUNO e COORDENADOR podem listar cursos
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public Map<String, Object> listar() {
        String usuario = getUsuarioLogado();
        List<Curso> cursos = service.listarTodos();

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("usuarioLogado", usuario);
        resposta.put("cursos", cursos);
        return resposta;
    }

    // ✅ Somente COORDENADOR pode criar curso
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public Map<String, Object> criar(@RequestBody Curso curso) {
        String usuario = getUsuarioLogado();
        Curso novoCurso = service.salvar(curso);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("usuarioLogado", usuario);
        resposta.put("cursoCriado", novoCurso);
        return resposta;
    }

    // ✅ Somente COORDENADOR pode atualizar curso
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public Map<String, Object> atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        String usuario = getUsuarioLogado();
        Curso atualizado = service.atualizar(id, curso);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("usuarioLogado", usuario);
        resposta.put("cursoAtualizado", atualizado);
        return resposta;
    }

    // ✅ Somente COORDENADOR pode excluir curso
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public Map<String, Object> excluir(@PathVariable Long id) {
        String usuario = getUsuarioLogado();
        service.excluir(id);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("usuarioLogado", usuario);
        resposta.put("mensagem", "Curso excluído com sucesso!");
        return resposta;
    }

    // Método auxiliar para pegar o usuário logado
    private String getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // retorna nome do usuário
        }
        return "Anônimo";
    }
}
