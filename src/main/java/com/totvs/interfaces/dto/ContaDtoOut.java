package com.totvs.interfaces.dto;


import com.totvs.domain.Conta;
import com.totvs.domain.enums.SituacaoEnum;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaDtoOut(Integer id, LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor,
                          String descricao, SituacaoEnum situacao) {


    public static ContaDtoOut converter(Conta conta) {
        if (ObjectUtils.isEmpty(conta)) {
            return null;
        }
        return new ContaDtoOut(conta.getId(), conta.getDataVencimento(),
                conta.getDataPagamento(), conta.getValor(),
                conta.getDescricao(), conta.getSituacao());

    }
}