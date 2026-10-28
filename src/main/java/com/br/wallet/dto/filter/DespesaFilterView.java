package com.br.wallet.dto.filter;

import com.br.wallet.entity.TipoDespesaEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record DespesaFilterView(
    List<String> tipos,
    UUID fornecedorUuid,
    LocalDate inicio,
    LocalDate termino,
    UUID formaPagamentoUuid
    ) {}
