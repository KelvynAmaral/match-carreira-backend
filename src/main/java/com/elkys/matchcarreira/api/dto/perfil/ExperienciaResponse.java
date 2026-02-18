package com.elkys.matchcarreira.api.dto.perfil;

import java.time.LocalDate;

public record ExperienciaResponse(String empresa, String cargo, LocalDate dataInicio, LocalDate dataFim, String descricao) {
    public ExperienciaResponse(com.elkys.matchcarreira.domain.model.ExperienciaProfissional exp) {
        this(exp.getEmpresa(), exp.getCargo(), exp.getDataInicio(), exp.getDataFim(), exp.getDescricao());
    }
}