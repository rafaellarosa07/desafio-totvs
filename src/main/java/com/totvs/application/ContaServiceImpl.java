package com.totvs.application;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.totvs.application.interfaces.ContaService;
import com.totvs.common.message.MessageUtil;
import com.totvs.domain.Conta;
import com.totvs.domain.enums.SituacaoEnum;
import com.totvs.domain.exception.NotFoundException;
import com.totvs.domain.exception.SaveException;
import com.totvs.domain.repository.ContaRepository;
import com.totvs.interfaces.dto.ContaDtoIn;
import com.totvs.interfaces.dto.ContaDtoOut;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaServiceImpl implements ContaService {

    private final ModelMapper modelMapper;

    private final ContaRepository contaRepository;

    public ContaServiceImpl(ModelMapper modelMapper, ContaRepository contaRepository) {
        this.modelMapper = modelMapper;
        this.contaRepository = contaRepository;
    }

    @Override
    @Transactional
    public ContaDtoOut cadastrar(ContaDtoIn contaDtoIn) {
        try {
            return ContaDtoOut.converter(contaRepository.save(
                    modelMapper.map(contaDtoIn, Conta.class)));
        } catch (Exception e) {
            throw new SaveException(MessageUtil.SALVAR_ERRO);
        }
    }

    @Override
    @Transactional
    public ContaDtoOut atualizar(ContaDtoIn contaDtoIn, Long id) {
        try {
            var conta = preencherConta(contaDtoIn, getConta(id));
            return ContaDtoOut.converter(contaRepository.save(conta));
        } catch (Exception e) {
            throw new SaveException(MessageUtil.SALVAR_ERRO);
        }
    }

    private Conta preencherConta(ContaDtoIn contaDtoIn, Conta conta) {
        conta.setDataVencimento(contaDtoIn.dataPagamento());
        conta.setValor(contaDtoIn.valor());
        conta.setDataPagamento(contaDtoIn.dataPagamento());
        conta.setDescricao(contaDtoIn.descricao());
        conta.setSituacao(contaDtoIn.situacao());
        return contaRepository.save(conta);
    }

    private Conta getConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageUtil.NOT_FOUND));
    }

    @Override
    public ContaDtoOut alterarSituacaoConta(Long id, SituacaoEnum situacaoEnum) {
        var conta = getConta(id);
        conta.setSituacao(situacaoEnum);
        contaRepository.save(conta);
        return ContaDtoOut.converter(conta);
    }

    @Override
    public Page<ContaDtoOut> buscarListaContasPagar(LocalDate localDate,
                                                   String descricao,
                                                   Pageable pageable) {
        var busca = contaRepository.findContasByFilter(
                localDate, descricao, pageable);
        var dtoList = busca.getContent()
                .stream()
                .map(b -> ContaDtoOut.converter(b))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, busca.getTotalElements());
    }

    @Override
    public ContaDtoOut buscarContaById(Long id) {
        return ContaDtoOut.converter(getConta(id));
    }

    @Override
    public Double getValorTotalPagoPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return contaRepository.findTotalPagoNoPeriodo(dataInicio, dataFim);
    }

    public List<ContaDtoOut> importarContas(MultipartFile file) throws IOException, CsvException {
        List<ContaDtoOut> contas = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            // Skip header
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                LocalDate dataVencimento = LocalDate.parse(line[0]);
                LocalDate dataPagamento = line[1].isEmpty() ? null : LocalDate.parse(line[1]);
                BigDecimal valor = new BigDecimal(line[2]);
                String descricao = line[3];
                SituacaoEnum situacao = SituacaoEnum.valueOf(line[4]);

                ContaDtoOut conta = new ContaDtoOut(null, dataVencimento, dataPagamento, valor, descricao, situacao);
                contas.add(conta);
            }
        }

        return contas;
    }


}
