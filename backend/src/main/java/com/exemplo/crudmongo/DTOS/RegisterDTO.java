package com.exemplo.crudmongo.DTOS;

import com.exemplo.crudmongo.Model.Role;

public record RegisterDTO(String nome, String email, String password, int idade, Role role) {
}
