package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.service.CurriculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios/{usuarioId}/curriculo")
@RequiredArgsConstructor
public class CurriculoController {

    private final CurriculoService curriculoService;

    @PostMapping
    public ResponseEntity<Curriculo> salvar(@PathVariable UUID usuarioId, @RequestBody Curriculo curriculo) {
        Curriculo salvo = curriculoService.salvar(usuarioId, curriculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Curriculo> buscar(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(curriculoService.buscarPorUsuario(usuarioId));
    }
}