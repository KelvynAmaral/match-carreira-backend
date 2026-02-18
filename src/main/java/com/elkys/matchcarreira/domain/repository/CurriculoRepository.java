package com.elkys.matchcarreira.domain.repository;

import com.elkys.matchcarreira.domain.model.Curriculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CurriculoRepository extends JpaRepository<Curriculo, UUID> {
    Optional<Curriculo> findByUsuarioId(UUID usuarioId);
}