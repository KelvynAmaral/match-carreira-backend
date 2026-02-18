package com.elkys.matchcarreira.api.dto;

import java.time.LocalDate;

public record FormacaoRequest(
        String instituicao,
        String curso,
        String grau, // Ex: Graduação, Pós, Especialização
        LocalDate dataInicio,
        LocalDate dataFim,
        boolean atual
) {

}