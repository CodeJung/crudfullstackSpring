package com.exemplo.crudmongo.DTOS;

import com.exemplo.crudmongo.Model.Role;

public record RegisterDTO(String email, String password, Role role) {
}
