package com.elkys.matchcarreira.api.dto.usuario;

import com.elkys.matchcarreira.domain.model.Usuario;
import java.util.UUID;

public record UsuarioResponse(UUID id, String nome, String email) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}