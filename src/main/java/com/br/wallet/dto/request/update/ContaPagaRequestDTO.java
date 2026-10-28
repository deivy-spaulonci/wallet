package com.br.wallet.dto.request.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContaPagaRequestDTO(
        @NotBlank(message = "Código da Conta inválida!")
        UUID uuid,
        @NotBlank(message = "Código da Forma de Pagamento inválida!")
        UUID formaPagamentoUuid,
        @NotBlank(message = "Data do Pagamento inválido!")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dataPagamento,
        @NotBlank(message = "Valor do Pagamento inválido!")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        BigDecimal valorPagamento) {
}
