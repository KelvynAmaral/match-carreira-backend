package com.elkys.matchcarreira.api.dto;

import java.time.Instant;
import java.util.List;

public record ErroResponse(
        int status,
        String mensagem,
        Instant timestamp,
        List<String> detalhes
) {}