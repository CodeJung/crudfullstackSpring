package com.exemplo.crudmongo.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ALUNO(
            Set.of(
                    Permission.ALUNO_READ
            )
    ),
    COORDENADOR(
            Set.of(
                    Permission.COORDENADOR_CREATE,
                    Permission.COORDENADOR_READ,
                    Permission.COORDENADOR_UPDATE,
                    Permission.COORDENADOR_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
