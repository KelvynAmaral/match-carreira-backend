package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.usuario.UsuarioRequest;
import com.elkys.matchcarreira.api.dto.usuario.UsuarioResponse; // Importa a nova Response
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody @Valid UsuarioRequest dto) {
        var usuario = usuarioService.cadastrar(dto);
        // Retorna apenas o essencial após o cadastro
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        // Converte a lista de entidades para uma lista de DTOs leves
        var usuarios = usuarioService.listarTodos().stream()
                .map(UsuarioResponse::new)
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}