package com.totvs.interfaces.dto;


import com.totvs.domain.enums.SituacaoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaDtoIn(LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor,
                         String descricao, SituacaoEnum situacao) {


}