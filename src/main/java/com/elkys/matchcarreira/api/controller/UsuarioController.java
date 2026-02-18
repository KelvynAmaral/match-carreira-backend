package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.UsuarioRequest;
import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.service.CurriculoService; // Import necessário
import com.elkys.matchcarreira.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CurriculoService curriculoService;

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioRequest dto) {
        var usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/meu-perfil")
    public ResponseEntity<Curriculo> obterDadosDoUsuarioLogado() {
        //SecurityFilter
        var usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        var curriculo = curriculoService.buscarPorUsuarioId(usuarioLogado.getId());

        return ResponseEntity.ok(curriculo);
    }
}