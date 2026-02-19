package com.elkys.matchcarreira.domain.model.perfil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "experiencias_profissionais")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ExperienciaProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String empresa;
    private String cargo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean atual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    @JsonIgnore
    private Curriculo curriculo;
}