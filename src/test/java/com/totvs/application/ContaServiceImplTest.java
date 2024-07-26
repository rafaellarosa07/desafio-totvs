package com.totvs.application;

import com.totvs.application.interfaces.ContaService;
import com.totvs.common.message.MessageUtil;
import com.totvs.domain.Conta;
import com.totvs.domain.enums.SituacaoEnum;
import com.totvs.domain.exception.NotFoundException;
import com.totvs.domain.exception.SaveException;
import com.totvs.domain.repository.ContaRepository;
import com.totvs.interfaces.dto.ContaDtoIn;
import com.totvs.interfaces.dto.ContaDtoOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaServiceImpl contaService;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        conta = new Conta();
        conta.setId(1);
        conta.setDescricao("Teste");
        conta.setValor(BigDecimal.valueOf(100));
        conta.setSituacao(SituacaoEnum.NAO_PAGO);
        conta.setDataPagamento(LocalDate.now());
        conta.setDataVencimento(LocalDate.now());
    }

    @Test
    public void testCadastrar_Success() {
        ContaDtoIn dtoIn = new ContaDtoIn(
                LocalDate.now(), // dataPagamento,
                LocalDate.now().plusDays(30), // dataVencimento
                BigDecimal.valueOf(100),
                "Teste",         // descricao
                SituacaoEnum.NAO_PAGO // situacao
        );

        when(modelMapper.map(any(ContaDtoIn.class), any())).thenReturn(conta);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);


        ContaDtoOut result = contaService.cadastrar(dtoIn);

        assertThat(result).isNotNull();
    }

    @Test
    public void testCadastrar_Exception() {
        ContaDtoIn dtoIn = new ContaDtoIn(
                LocalDate.now(), // dataPagamento,
                LocalDate.now().plusDays(30), // dataVencimento
                BigDecimal.valueOf(100),
                "Teste",         // descricao
                SituacaoEnum.NAO_PAGO // situacao
        );
        when(modelMapper.map(any(ContaDtoIn.class), any())).thenReturn(conta);
        when(contaRepository.save(any(Conta.class))).thenThrow(new RuntimeException());

        assertThrows(SaveException.class, () -> contaService.cadastrar(dtoIn));
    }

    @Test
    public void testAtualizar_Success() {
        ContaDtoIn dtoIn = new ContaDtoIn(
                LocalDate.now(), // dataPagamento,
                LocalDate.now().plusDays(30), // dataVencimento
                BigDecimal.valueOf(100),
                "Teste",         // descricao
                SituacaoEnum.PAGO // situacao
        );

        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);


        ContaDtoOut result = contaService.atualizar(dtoIn, 1L);

        assertThat(result).isNotNull();
    }

    @Test
    public void testAtualizar_NotFound() {
        ContaDtoIn dtoIn = new ContaDtoIn(
                LocalDate.now(), // dataPagamento,
                LocalDate.now().plusDays(30), // dataVencimento
                BigDecimal.valueOf(100),
                "Teste",         // descricao
                SituacaoEnum.NAO_PAGO // situacao
        );
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SaveException.class, () -> contaService.atualizar(dtoIn, 1L));
    }

    @Test
    public void testAlterarSituacaoConta_Success() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        ContaDtoOut result = contaService.alterarSituacaoConta(1L, SituacaoEnum.PAGO);

        assertThat(result).isNotNull();
        assertThat(result.situacao()).isEqualTo(SituacaoEnum.PAGO);
    }

    @Test
    public void testAlterarSituacaoConta_NotFound() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> contaService.alterarSituacaoConta(1L, SituacaoEnum.PAGO));
    }

    @Test
    public void testBuscarListaContasPagar_Success() {
        Page<Conta> page = new PageImpl<>(List.of(conta));
        when(contaRepository.findContasByFilter(any(), any(), any(Pageable.class))).thenReturn(page);


        Page<ContaDtoOut> result = contaService.buscarListaContasPagar(LocalDate.now(), "Teste", Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    public void testBuscarContaById_Success() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));


        ContaDtoOut result = contaService.buscarContaById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    public void testBuscarContaById_NotFound() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> contaService.buscarContaById(1L));
    }

    @Test
    public void testGetValorTotalPagoPeriodo_Success() {
        when(contaRepository.findTotalPagoNoPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(200.0);

        Double result = contaService.getValorTotalPagoPeriodo(LocalDate.now().minusDays(10), LocalDate.now());

        assertThat(result).isEqualTo(200.0);
    }
}
