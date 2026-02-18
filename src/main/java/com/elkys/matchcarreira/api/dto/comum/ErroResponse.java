package com.elkys.matchcarreira.api.dto.comum;

import java.time.Instant;
import java.util.List;

public record ErroResponse(
        int status,
        String mensagem,
        Instant timestamp,
        List<String> detalhes
) {}