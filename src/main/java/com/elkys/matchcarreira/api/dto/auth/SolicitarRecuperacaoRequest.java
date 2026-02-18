package com.elkys.matchcarreira.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarRecuperacaoRequest(@NotBlank @Email String email) {}

