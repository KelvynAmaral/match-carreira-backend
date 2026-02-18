package com.elkys.matchcarreira.domain.repository;

import com.elkys.matchcarreira.domain.model.TokenRecuperacaoSenha;
import com.elkys.matchcarreira.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, UUID> {

    Optional<TokenRecuperacaoSenha> findByToken(String token);

    void deleteByUsuario(Usuario usuario);
}