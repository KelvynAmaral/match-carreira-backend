package com.elkys.matchcarreira.api.controller;

import com.elkys.matchcarreira.api.dto.perfil.*;
import com.elkys.matchcarreira.domain.model.Usuario;
import com.elkys.matchcarreira.domain.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meu-perfil")
@RequiredArgsConstructor
@Tag(name = "Perfil", description = "Gestão híbrida de dados profissionais e de conta")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    @Operation(summary = "Obtém os dados completos do perfil via Cache")
    public ResponseEntity<PerfilResponse> obterDadosDoUsuarioLogado(@AuthenticationPrincipal Usuario logado) {
        return ResponseEntity.ok(perfilService.buscarPorUsuario(logado.getId()));
    }

    @PutMapping
    @Operation(summary = "Atualiza nome, contato e dados base do currículo")
    public ResponseEntity<PerfilResponse> atualizarMeuPerfil(
            @AuthenticationPrincipal Usuario logado,
            @RequestBody @Valid PerfilAtualizacaoRequest dto) {
        return ResponseEntity.ok(perfilService.atualizarCompleto(logado.getId(), dto));
    }

    // --- EXPERIÊNCIAS ---

    @PostMapping("/experiencias")
    @Operation(summary = "Adiciona uma nova experiência profissional")
    public ResponseEntity<PerfilResponse> adicionarExperiencia(
            @AuthenticationPrincipal Usuario logado,
            @RequestBody @Valid ExperienciaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(perfilService.adicionarExperiencia(logado.getId(), dto));
    }

    @PutMapping("/experiencias/{id}")
    @Operation(summary = "Atualiza uma experiência existente")
    public ResponseEntity<PerfilResponse> atualizarExperiencia(
            @AuthenticationPrincipal Usuario logado,
            @PathVariable UUID id,
            @RequestBody @Valid ExperienciaRequest dto) {
        return ResponseEntity.ok(perfilService.atualizarExperiencia(logado.getId(), id, dto));
    }

    @DeleteMapping("/experiencias/{id}")
    @Operation(summary = "Remove uma experiência profissional")
    public ResponseEntity<Void> removerExperiencia(
            @AuthenticationPrincipal Usuario logado,
            @PathVariable UUID id) {
        perfilService.removerExperiencia(logado.getId(), id);
        return ResponseEntity.noContent().build();
    }

    // --- FORMAÇÕES ---

    @PostMapping("/formacoes")
    @Operation(summary = "Adiciona uma nova formação acadêmica")
    public ResponseEntity<PerfilResponse> adicionarFormacao(
            @AuthenticationPrincipal Usuario logado,
            @RequestBody @Valid FormacaoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(perfilService.adicionarFormacao(logado.getId(), dto));
    }

    @PutMapping("/formacoes/{id}")
    @Operation(summary = "Atualiza uma formação existente")
    public ResponseEntity<PerfilResponse> atualizarFormacao(
            @AuthenticationPrincipal Usuario logado,
            @PathVariable UUID id,
            @RequestBody @Valid FormacaoRequest dto) {
        return ResponseEntity.ok(perfilService.atualizarFormacao(logado.getId(), id, dto));
    }

    @DeleteMapping("/formacoes/{id}")
    @Operation(summary = "Remove uma formação acadêmica")
    public ResponseEntity<Void> removerFormacao(
            @AuthenticationPrincipal Usuario logado,
            @PathVariable UUID id) {
        perfilService.removerFormacao(logado.getId(), id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/competencias")
    @Operation(summary = "Atualiza a lista de competências (skills) do usuário")
    public ResponseEntity<PerfilResponse> atualizarCompetencias(
            @AuthenticationPrincipal Usuario logado,
            @RequestBody List<String> competencias) {
        return ResponseEntity.ok(perfilService.atualizarCompetencias(logado.getId(), competencias));
    }
}