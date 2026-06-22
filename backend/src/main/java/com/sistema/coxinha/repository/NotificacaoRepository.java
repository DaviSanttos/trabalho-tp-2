package com.sistema.coxinha.repository;

import com.sistema.coxinha.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findAllByOrderByDataDesc();
    List<Notificacao> findByLidaFalseOrderByDataDesc();
    long countByLidaFalse();
}
