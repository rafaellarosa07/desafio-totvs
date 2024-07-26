package com.totvs.domain;


import com.totvs.domain.enums.SituacaoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conta")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_vencimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private LocalDate dataPagamento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "situacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao;


}