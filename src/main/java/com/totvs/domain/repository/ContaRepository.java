package com.totvs.domain.repository;

import com.totvs.domain.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("Select c from Conta c where (:dataVencimento is null or :dataVencimento = c.dataVencimento and " +
            ":descricao is null or :descricao = c.descricao)")
    Page<Conta> findContasByFilter(LocalDate dataVencimento, String descricao, Pageable pageable);


    @Query("SELECT SUM(c.valor) FROM Conta c WHERE c.dataPagamento BETWEEN :dataInicio AND :dataFim")
    Double findTotalPagoNoPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}
