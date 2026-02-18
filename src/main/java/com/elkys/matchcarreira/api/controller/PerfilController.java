package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.perfil.*; // Importa o novo PerfilResponse e os Requests
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.service.CurriculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/meu-perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final CurriculoService curriculoService;

    @GetMapping
    public ResponseEntity<PerfilResponse> obterDadosDoUsuarioLogado() {
        var curriculo = curriculoService.buscarPorUsuarioId(getUsuarioLogado().getId());
        return ResponseEntity.ok(new PerfilResponse(curriculo));
    }

    @PutMapping
    public ResponseEntity<PerfilResponse> atualizarMeuPerfil(@RequestBody @Valid PerfilAtualizacaoRequest dto) {
        var atualizado = curriculoService.atualizarPerfilCompleto(getUsuarioLogado().getId(), dto);
        return ResponseEntity.ok(new PerfilResponse(atualizado));
    }

    // --- EXPERIÊNCIAS ---
    @PostMapping("/experiencias")
    public ResponseEntity<PerfilResponse> adicionarExperiencia(@RequestBody @Valid ExperienciaRequest dto) {
        var atualizado = curriculoService.adicionarExperiencia(getUsuarioLogado().getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilResponse(atualizado));
    }

    @PutMapping("/experiencias/{id}")
    public ResponseEntity<PerfilResponse> atualizarExperiencia(@PathVariable UUID id, @RequestBody @Valid ExperienciaRequest dto) {
        var atualizado = curriculoService.atualizarExperiencia(getUsuarioLogado().getId(), id, dto);
        return ResponseEntity.ok(new PerfilResponse(atualizado));
    }

    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<Void> removerExperiencia(@PathVariable UUID id) {
        curriculoService.removerExperiencia(getUsuarioLogado().getId(), id);
        return ResponseEntity.noContent().build();
    }

    // --- FORMAÇÕES ---
    @PostMapping("/formacoes")
    public ResponseEntity<PerfilResponse> adicionarFormacao(@RequestBody @Valid FormacaoRequest dto) {
        var atualizado = curriculoService.adicionarFormacao(getUsuarioLogado().getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilResponse(atualizado));
    }

    @PutMapping("/formacoes/{id}")
    public ResponseEntity<PerfilResponse> atualizarFormacao(@PathVariable UUID id, @RequestBody @Valid FormacaoRequest dto) {
        var atualizado = curriculoService.atualizarFormacao(getUsuarioLogado().getId(), id, dto);
        return ResponseEntity.ok(new PerfilResponse(atualizado));
    }

    @DeleteMapping("/formacoes/{id}")
    public ResponseEntity<Void> removerFormacao(@PathVariable UUID id) {
        curriculoService.removerFormacao(getUsuarioLogado().getId(), id);
        return ResponseEntity.noContent().build();
    }

    // --- MÉTODO AUXILIAR ---
    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}