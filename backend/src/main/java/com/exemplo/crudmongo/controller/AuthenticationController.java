package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.DTOS.AuthenticationDTO;
import com.exemplo.crudmongo.DTOS.LoginResponseDTO;
import com.exemplo.crudmongo.DTOS.RegisterDTO;
import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;
import com.exemplo.crudmongo.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Pessoa) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Pessoa pessoa = new Pessoa(data.email(), encryptedPassword, data.role());
        pessoa.setNome(data.nome());
        pessoa.setIdade(data.idade());

        this.repository.save(pessoa);

        return ResponseEntity.ok().build();
    }
}
