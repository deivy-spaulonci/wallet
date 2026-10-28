package com.br.wallet.entity.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "list_contas_view", schema = "public")
@Immutable
public class ListContasView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    private String nome;
    @Column(name = "tipo_uuid")
    private UUID tipoUuid;
    private Boolean ativo;
    private LocalDate vencimento;
    private Integer parcela;
    @Column(name = "total_parcela")
    private Integer totalParcela;
    private BigDecimal valor;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
}
