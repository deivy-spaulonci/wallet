package com.br.wallet.dto.mapper;

import com.br.wallet.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.wallet.dto.request.update.PessoaFisicaRequestUpdateDTO;
import com.br.wallet.dto.response.PessoaFisicaResponseDTO;
import com.br.wallet.entity.PessoaFisica;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PessoaFisicaMapper {
    PessoaFisicaMapper INSTANCE = Mappers.getMapper(PessoaFisicaMapper.class);

    PessoaFisica toEntity(PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO);

    PessoaFisica toEntity(PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO);

    PessoaFisicaResponseDTO toResponseDTO(PessoaFisica pessoaFisica);

    PessoaFisica parcialUpdate(PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO);

    List<PessoaFisicaResponseDTO> toListResponseDTO(List<PessoaFisica> pessoaFisicas);

}
