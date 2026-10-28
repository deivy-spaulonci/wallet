package com.br.wallet.dto.mapper;


import com.br.wallet.dto.request.create.TipoContaRequestCreateDTO;
import com.br.wallet.dto.request.update.TipoContaRequestUpdateDTO;
import com.br.wallet.dto.response.TipoContaResponseDTO;
import com.br.wallet.entity.TipoConta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);

    List<TipoContaResponseDTO>  toListTipoContaResponseDTO(List<TipoConta> tiposConta);

    TipoContaResponseDTO toTipoContaResponseDTO(TipoConta tipoConta);

    TipoConta toEntity(TipoContaRequestUpdateDTO tipoContaRequestUpdateDTO);

    TipoConta toEntity(TipoContaRequestCreateDTO tipoContaRequestCreateDTO);
}
