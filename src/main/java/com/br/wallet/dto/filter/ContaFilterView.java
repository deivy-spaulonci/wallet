package com.br.wallet.dto.filter;

import com.br.wallet.entity.ContaStatusEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
public record ContaFilterView(
    UUID tipoUuid,
    LocalDate vencimentoInicial,
    LocalDate vencimentoFinal,
    Set<ContaStatusEnum> contaStatus
){}
