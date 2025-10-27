package com.exemplo.crudmongo.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    COORDENADOR_CREATE("coordenador:create"),
    COORDENADOR_READ("coordenador:read"),
    COORDENADOR_UPDATE("coordenador:update"),
    COORDENADOR_DELETE("coordenador:delete"),
    ALUNO_READ("aluno:read");

    @Getter
    private final String permission;
}
