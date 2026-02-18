package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.domain.model.Curriculo;
import com.elkys.matchcarreira.domain.service.CurriculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios/{usuarioId}/curriculo")
@RequiredArgsConstructor
public class CurriculoController {

    private final CurriculoService curriculoService;

    // Mudamos para PUT porque o curriculo já nasce com o Usuario (via UsuarioService)
    @PutMapping
    public ResponseEntity<Curriculo> atualizar(@PathVariable UUID usuarioId, @RequestBody Curriculo curriculo) {
        Curriculo salvo = curriculoService.atualizar(usuarioId, curriculo);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<Curriculo> buscar(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(curriculoService.buscarPorUsuarioId(usuarioId));
    }
}