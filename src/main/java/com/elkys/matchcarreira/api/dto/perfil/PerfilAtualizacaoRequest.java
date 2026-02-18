package com.elkys.matchcarreira.api.dto.perfil;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PerfilAtualizacaoRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\([1-9]{2}\\) 9[0-9]{4}\\-[0-9]{4}$", message = "Formato: (DD) 99999-9999")
        String telefone,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data deve ser no passado")
        LocalDate dataNascimento,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "Use a sigla (ex: MG)")
        String estado,

        String resumoProfissional,
        String cargoDesejado
) {}