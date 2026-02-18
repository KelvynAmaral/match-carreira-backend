package com.elkys.matchcarreira.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "curriculos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "resumo_profissional", columnDefinition = "TEXT")
    private String resumoProfissional;

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ExperienciaProfissional> experiencias = new ArrayList<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FormacaoAcademica> formacoes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "curriculo_competencias", joinColumns = @JoinColumn(name = "curriculo_id"))
    @Column(name = "competencia")
    private List<String> competencias = new ArrayList<>();
}