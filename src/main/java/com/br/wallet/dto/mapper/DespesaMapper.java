package com.br.wallet.dto.mapper;

import com.br.wallet.dto.ref.TipoDespesaRefDTO;
import com.br.wallet.dto.request.create.DespesaRequestCreateDTO;
import com.br.wallet.dto.request.update.DespesaRequestUpdateDTO;
import com.br.wallet.dto.response.DespesaResponseDTO;
import com.br.wallet.dto.response.DespesaResponseViewDTO;
import com.br.wallet.entity.Despesa;
import com.br.wallet.entity.TipoDespesaEnum;
import com.br.wallet.entity.views.ListDespesasView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);

    List<DespesaResponseDTO> toListDespesaResponseDTO(List<Despesa> despesas);

    //@Mapping(source = "fornecedor", target = "fornecedor")
    DespesaResponseDTO toDespesaResponseDTO(Despesa despesa);

    DespesaResponseViewDTO toDespesaResponseViewDTO(ListDespesasView listDespesasView);

    Despesa toEntity(DespesaRequestCreateDTO despesaRequestCreateDTO);

    Despesa toEntity(DespesaRequestUpdateDTO despesaRequestUpdateDTO);

//    @Mapping(source = "dataPagamento", target = "dataPagamento", dateFormat = "ddMMyyyy")
//    @Mapping(source = "idFormaPagamento", target = "formaPagamento.id")
//    Despesa toEntity(DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO);

    default TipoDespesaEnum map(TipoDespesaRefDTO value) {
        return TipoDespesaEnum.forValues(value.value());
    }

}
