package com.totvs.interfaces.controllers;


import com.opencsv.exceptions.CsvException;
import com.totvs.application.interfaces.ContaService;
import com.totvs.domain.enums.SituacaoEnum;
import com.totvs.interfaces.dto.ContaDtoIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("conta")
@PreAuthorize("hasRole('USER')")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova conta", description = "Adiciona uma nova conta ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaDtoIn.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> cadastrar(@RequestBody ContaDtoIn contaDtoIn) {
        try {
            var createdConta = contaService.cadastrar(contaDtoIn);
            return new ResponseEntity<>(createdConta, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma conta existente", description = "Atualiza uma conta existente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaDtoIn.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> atualizar(@RequestBody ContaDtoIn contaDtoIn, @PathVariable Long id) {
        try {
            var updatedConta = contaService.atualizar(contaDtoIn, id);
            return new ResponseEntity<>(updatedConta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PatchMapping("/{id}/situacao")
    @Operation(summary = "Alterar a situação de uma conta", description = "Altera a situação de uma conta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Situação da conta alterada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaDtoIn.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> alterarSituacaoConta(@PathVariable Long id, @RequestParam SituacaoEnum situacaoEnum) {
        try {
            var updatedConta = contaService.alterarSituacaoConta(id, situacaoEnum);
            return new ResponseEntity<>(updatedConta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Buscar lista de contas a pagar", description = "Busca uma lista de contas a pagar, com filtros opcionais por data e descrição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contas encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> buscarListaContasPagar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,
            @RequestParam(required = false) String descricao,
            Pageable pageable) {
        try {
            var contas = contaService.buscarListaContasPagar(localDate, descricao, pageable);
            return new ResponseEntity<>(contas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Busca uma conta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaDtoIn.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> buscarContaById(@PathVariable Long id) {
        try {
            var conta = contaService.buscarContaById(id);
            return new ResponseEntity<>(conta, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/totalPago")
    @Operation(summary = "Obter valor total pago em um período", description = "Obtém o valor total pago em um período específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valor total pago encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> getValorTotalPagoPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        Double totalPago = contaService.getValorTotalPagoPeriodo(dataInicio, dataFim);
        try {
            return new ResponseEntity<>(totalPago, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/importar")
    public ResponseEntity<?> importarContas(@RequestPart("file") MultipartFile file) {
        try {
            var contas = contaService.importarContas(file);
            return ResponseEntity.ok(contas);
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(500).body(e.getMessage()); // Pode ser melhorado para fornecer mais informações sobre o erro
        }
    }
}
