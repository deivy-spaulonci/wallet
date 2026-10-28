package com.br.wallet.dto.mapper;

import com.br.wallet.dto.response.FormaPagamentoResponseDTO;
import com.br.wallet.entity.FormaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);

    List<FormaPagamentoResponseDTO> toListPagamentoResponseDTO(List<FormaPagamento> formasPagamento);

    FormaPagamentoResponseDTO toDTO(FormaPagamento formaPagamento);
}
