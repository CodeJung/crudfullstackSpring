package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.dto.CursoRequestDTO;
import com.exemplo.crudmongo.dto.CursoResponseDTO;
import com.exemplo.crudmongo.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<CursoResponseDTO> listarCursos(Authentication authentication) {
        // Verifica se o usuário autenticado é COORDENADOR
        boolean isCoordenador = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_COORDENADOR"));

        List<Curso> cursos;
        if (isCoordenador) {
            cursos = cursoRepository.findAll(); // Coordenador vê todos
        } else {
            cursos = cursoRepository.findAllByAtivoTrue(); // Aluno vê apenas ativos
        }
        
        return cursos.stream().map(CursoResponseDTO::new).collect(Collectors.toList());
    }

    public CursoResponseDTO buscarCursoPorId(String id, Authentication authentication) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        boolean isAluno = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ALUNO"));

        // Se for aluno e o curso estiver inativo, negue o acesso.
        if (isAluno && !curso.isAtivo()) {
             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado a este curso");
        }

        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO criarCurso(CursoRequestDTO dto) {
        Curso novoCurso = new Curso();
        novoCurso.setNome(dto.getNome());
        novoCurso.setCargaHoraria(dto.getCargaHoraria());
        novoCurso.setAtivo(true); // Sempre criado como ativo

        Curso cursoSalvo = cursoRepository.save(novoCurso);
        return new CursoResponseDTO(cursoSalvo);
    }

    public CursoResponseDTO atualizarCurso(String id, CursoRequestDTO dto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        curso.setNome(dto.getNome());
        curso.setCargaHoraria(dto.getCargaHoraria());
        // Nota: Este método não reativa um curso. 
        // Para reativar, seria necessário um método PATCH ou um DTO diferente.

        Curso cursoAtualizado = cursoRepository.save(curso);
        return new CursoResponseDTO(cursoAtualizado);
    }

    public void desativarCurso(String id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        curso.setAtivo(false); // Exclusão Lógica
        cursoRepository.save(curso);
    }
}