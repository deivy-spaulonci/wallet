package com.br.wallet.dto.mapper;

import com.br.wallet.dto.request.create.ContaRequestCreateDTO;
import com.br.wallet.dto.request.update.ContaRequestUpdateDTO;
import com.br.wallet.dto.response.ContaResponseDTO;
import com.br.wallet.dto.response.ContaResponseViewDTO;
import com.br.wallet.entity.Conta;
import com.br.wallet.entity.views.ListContasView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    List<ContaResponseDTO> toListContaResponseDTO(List<Conta> contas);

    ContaResponseDTO toContaResponseDTO(Conta conta);

    Conta toEntity(ContaRequestCreateDTO contaRequestCreateDTO);

    Conta toEntity(ContaRequestUpdateDTO contaRequestUpdateDTO);

    ContaResponseViewDTO toContaResponseViewDTO(ListContasView listContasView);
}
