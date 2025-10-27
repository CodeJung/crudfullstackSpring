package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.dto.CursoRequestDTO;
import com.exemplo.crudmongo.dto.CursoResponseDTO;
import com.exemplo.crudmongo.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    // Permitido para ALUNO e COORDENADOR (lógica de o que mostrar está no service)
    @GetMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'ALUNO')")
    public List<CursoResponseDTO> listarCursos(Authentication authentication) {
        return cursoService.listarCursos(authentication);
    }

    // Permitido para ALUNO e COORDENADOR (lógica de acesso está no service)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'ALUNO')")
    public CursoResponseDTO buscarCursoPorId(@PathVariable String id, Authentication authentication) {
        return cursoService.buscarCursoPorId(id, authentication);
    }

    // Apenas COORDENADOR
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponseDTO> criarCurso(@RequestBody @Valid CursoRequestDTO dto) {
        CursoResponseDTO cursoSalvo = cursoService.criarCurso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }

    // Apenas COORDENADOR
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public CursoResponseDTO atualizarCurso(@PathVariable String id, @RequestBody @Valid CursoRequestDTO dto) {
        return cursoService.atualizarCurso(id, dto);
    }

    // Apenas COORDENADOR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> desativarCurso(@PathVariable String id) {
        cursoService.desativarCurso(id);
        return ResponseEntity.noContent().build();
    }
}