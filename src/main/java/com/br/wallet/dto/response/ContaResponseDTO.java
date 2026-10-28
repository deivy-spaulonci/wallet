package com.br.wallet.dto.response;

import com.br.wallet.entity.ContaStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContaResponseDTO(
    String codigoBarras,
    TipoContaResponseDTO tipoConta,
    LocalDate emissao,
    LocalDate vencimento,
    Integer parcela,
    Integer totalParcela,
    BigDecimal valor,
    LocalDate dataPagamento,
    FormaPagamentoResponseDTO formaPagamento,
    BigDecimal valorPago,
    String titulo,
    String comprovante,
    String observacao,
    UUID uuid,
    String status){

    public String getFormaPagamento()
    {
        if(formaPagamento != null && formaPagamento.modalidade()!=null){
            String forma = formaPagamento.modalidade().descricao();
            forma += formaPagamento.descricao()==null ? "" : " - " + formaPagamento.descricao();
            return forma;
        }
        return "";
    }

    public int getIntStatus() {
        if(vencimento()!=null && dataPagamento()==null){
            if(vencimento().isAfter(LocalDate.now()))
                return 1;
            if(vencimento().isEqual(LocalDate.now()))
                return 0;
            if(vencimento().isBefore(LocalDate.now()))
                return -1;
        }
        return 2;
    }

    public String getStatus() {
        return switch (getIntStatus()) {
            case 1 -> ContaStatusEnum.ABERTO.getNome();
            case 0 -> ContaStatusEnum.HOJE.getNome();
            case -1 -> ContaStatusEnum.ATRASADO.getNome();
            case 2 -> ContaStatusEnum.PAGO.getNome();
            default -> ContaStatusEnum.PAGO.getNome();
        };
    }
}
