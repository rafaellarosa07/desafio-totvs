package com.totvs.application.interfaces;

import com.opencsv.exceptions.CsvException;
import com.totvs.domain.enums.SituacaoEnum;
import com.totvs.interfaces.dto.ContaDtoIn;
import com.totvs.interfaces.dto.ContaDtoOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ContaService {

    public ContaDtoOut cadastrar(ContaDtoIn contaDtoIn);

    public ContaDtoOut atualizar(ContaDtoIn contaDtoIn, Long id);

    public ContaDtoOut alterarSituacaoConta(Long id, SituacaoEnum situacaoEnum);

    public Page<ContaDtoOut> buscarListaContasPagar(LocalDate localDate, String descricao, Pageable pageable);

    public ContaDtoOut buscarContaById(Long id);

    public Double getValorTotalPagoPeriodo(LocalDate dataInicio, LocalDate dataFim);

    public List<ContaDtoOut> importarContas(MultipartFile file) throws IOException, CsvException;

}
