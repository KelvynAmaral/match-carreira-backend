package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "experiencias_profissionais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    @JsonBackReference
    private Curriculo curriculo;

    private String empresa;
    private String cargo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "trabalho_atual")
    private boolean trabalhoAtual;
}