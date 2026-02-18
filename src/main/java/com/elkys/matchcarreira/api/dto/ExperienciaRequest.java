package com.elkys.matchcarreira.api.dto;

import java.time.LocalDate;

public record ExperienciaRequest(
        String empresa,
        String cargo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        boolean atual
) {

}