package com.elkys.matchcarreira.api.dto.perfil;

import java.time.LocalDate;

public record FormacaoResponse(String instituicao, String curso, LocalDate dataInicio, LocalDate dataFim) {
    public FormacaoResponse(com.elkys.matchcarreira.domain.model.FormacaoAcademica form) {
        this(form.getInstituicao(), form.getCurso(), form.getDataInicio(), form.getDataFim());
    }
}