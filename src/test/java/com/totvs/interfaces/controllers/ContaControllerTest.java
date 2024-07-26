//package com.totvs.interfaces.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.totvs.DesafioTotvsApplication;
//import com.totvs.application.ContaServiceImpl;
//import com.totvs.application.interfaces.ContaService;
//import com.totvs.domain.enums.SituacaoEnum;
//import com.totvs.interfaces.dto.ContaDtoIn;
//import com.totvs.interfaces.dto.ContaDtoOut;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.math.BigDecimal;
//import java.net.URI;
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(DesafioTotvsApplication.class)
//public class ContaControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private ContaServiceImpl contaService;
//    private ContaDtoIn contaDtoIn;
//    private ContaDtoOut contaDtoOut;
//
//    @InjectMocks
//    private ContaController contaController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        contaDtoIn =  new ContaDtoIn(LocalDate.of(2020,05,05),
//                LocalDate.of(2020,05,05), BigDecimal.TEN, "pago", SituacaoEnum.NAO_PAGO);
//
//        contaDtoOut = new ContaDtoOut(
//                1,
//                LocalDate.now(), // dataPagamento,
//                LocalDate.now().plusDays(30), // dataVencimento
//                BigDecimal.valueOf(100),
//                "Teste",         // descricao
//                SituacaoEnum.NAO_PAGO // situacao
//        );
//        contaController = new ContaController(contaService);
//
//    }
//
//    @Test
//    public void testCadastrar() throws Exception {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
//
//        when(contaService.cadastrar(contaDtoIn)).thenReturn(contaDtoOut);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/conta")
//                        .headers(headers)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(contaDtoOut)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//    }
//
//    @Test
//    public void testAtualizar() throws Exception {
//        Long id = 1L;
//        // Configure o DTO com valores de teste
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
//
//        when(contaService.cadastrar(contaDtoIn)).thenReturn(contaDtoOut);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/conta/"+id)
//                        .headers(headers)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(contaDtoOut)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//    }
//
////    @Test
////    public void testAlterarSituacaoConta() throws Exception {
////        Long id = 1L;
////        SituacaoEnum situacaoEnum = SituacaoEnum.PAGO;
////
////        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
////                .pathSegment(id.toString(), "situacao")
////                .queryParam("situacaoEnum", situacaoEnum)
////                .build()
////                .toUri();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
////        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
////
////        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PATCH, requestEntity, String.class);
////
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////        // Adicione mais asserções baseadas na resposta
////    }
////
////    @Test
////    public void testBuscarListaContasPagar() throws Exception {
////        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
////                .queryParam("localDate", LocalDate.now())
////                .queryParam("descricao", "Teste")
////                .build()
////                .toUri();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
////        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
////
////        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
////
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////        // Adicione mais asserções baseadas na resposta
////    }
////
////    @Test
////    public void testBuscarContaById() throws Exception {
////        Long id = 1L;
////
////        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
////                .pathSegment(id.toString())
////                .build()
////                .toUri();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
////        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
////
////        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
////
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////        // Adicione mais asserções baseadas na resposta
////    }
////
////    @Test
////    public void testGetValorTotalPagoPeriodo() throws Exception {
////        LocalDate dataInicio = LocalDate.now().minusDays(10);
////        LocalDate dataFim = LocalDate.now();
////
////        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
////                .pathSegment("totalPago")
////                .queryParam("dataInicio", dataInicio)
////                .queryParam("dataFim", dataFim)
////                .build()
////                .toUri();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Bearer <TOKEN_DE_TESTE>"); // Substitua com um token de teste se necessário
////        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
////
////        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
////
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////        // Adicione mais asserções baseadas na resposta
////    }
//}
